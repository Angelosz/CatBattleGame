package angelosz.catbattlegame.ui.combat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CampaignRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.EnemyCatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.CombatModifier
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.CombatStage
import angelosz.catbattlegame.domain.enums.CombatState
import angelosz.catbattlegame.domain.enums.DamageType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.EnemyType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class CombatScreenViewModel(
    private val playerAccountRepository: PlayerAccountRepository,
    private val campaignRepository: CampaignRepository,
    private val enemyCatRepository: EnemyCatRepository,
    private val abilityRepository: AbilityRepository,
    private val catRepository: CatRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CombatScreenUiState())
    val uiState: StateFlow<CombatScreenUiState> = _uiState

    private suspend fun getCatAbilities(id: Int): List<Ability> = abilityRepository.getCatAbilities(id)
    private fun getCat(catId: Int): CombatCat? = (_uiState.value.teamCombatCats + _uiState.value.enemyCombatCats).find { catId == it.cat.combatId }
    fun playerCatIsActive(): CombatCat? = _uiState.value.teamCombatCats.find { _uiState.value.activeCatId == it.cat.combatId }

    fun setupTeamsData(chapterId: Long, teamId: Long){
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING,
                combatState = CombatState.LOADING,
                combatStage = CombatStage.PLAYER_TURN
            )
        }

        viewModelScope.launch {
            try{
                val chapter = campaignRepository.getChapterById(chapterId)
                val chapterEnemies = enemyCatRepository.getEnemyCatsByCampaignChapterId(chapterId)
                val playerTeam = playerAccountRepository.getPlayerTeamById(teamId)
                val playerTeamData = playerAccountRepository.getTeamData(teamId)
                val playerCombatCats = buildPlayerCombatCatsTeam(
                    teamData = playerTeamData,
                    firstCombatId = 0
                )
                val enemyCombatCats = buildEnemyCombatCatsTeam(
                    chapterEnemies = chapterEnemies,
                    firstCombatId = playerCombatCats.size
                )

                _uiState.update{
                    it.copy(
                        chapter = chapter,
                        chapterEnemies = chapterEnemies,
                        team = TeamData(
                            teamId = playerTeam.id,
                            teamName = playerTeam.name,
                            cats = playerTeamData),
                        screenState = ScreenState.SUCCESS,
                        combatState = CombatState.READY_TO_START,
                        teamCombatCats = playerCombatCats,
                        enemyCombatCats = enemyCombatCats
                    )
                }
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    screenState = ScreenState.FAILURE,
                    combatState = CombatState.NOT_LOADED
                )
            }
        }
    }

    private suspend fun buildPlayerCombatCatsTeam(teamData: List<BasicCatData>, firstCombatId: Int): List<CombatCat> {
        return teamData.mapIndexed { index, data ->
            val ownedCat = playerAccountRepository.getOwnedCatByCatId(data.catId)
            val cat = catRepository.getCatById(ownedCat.catId)

            val combatAbilities = CombatAbility.build(getCatAbilities(cat.id))

            PlayerCombatCat(
                CombatCatData.build(
                    combatId = firstCombatId + index,
                    cat = cat,
                    ownedCat = ownedCat,
                    combatAbilities = combatAbilities
                ),
                onDeath = { catId ->
                    playerCatDied(catId)
                }
            )
        }
    }
    private suspend fun buildEnemyCombatCatsTeam(chapterEnemies: List<EnemyCat>, firstCombatId: Int): List<EnemyCombatCat> {
        return chapterEnemies.mapIndexed { index, enemyCat ->
            createEnemyCombatCat(enemyCat, firstCombatId + index)
        }
    }

    private suspend fun createEnemyCombatCat(
        enemyCat: EnemyCat,
        combatId: Int,
    ): EnemyCombatCat {
        val catAbilities = abilityRepository.getEnemyCatAbilities(enemyCat.id.toInt())
        val combatAbilities = CombatAbility.build(catAbilities)
        return when (enemyCat.enemyType) {
            EnemyType.SIMPLE_ENEMY -> SimpleEnemy(
                CombatCatData.build(
                    combatId = combatId,
                    enemyCat = enemyCat,
                    combatAbilities = combatAbilities
                ),
                onDeath = { catId ->
                    enemyCatDied(catId)
                }
            )

            EnemyType.SUMMONER -> SummonerEnemy(
                CombatCatData.build(
                    combatId = combatId,
                    enemyCat = enemyCat,
                    combatAbilities = combatAbilities
                ),
                onDeath = { catId ->
                    enemyCatDied(catId)
                }
            )

            EnemyType.UNIQUE_SUMMONER -> UniqueSummonEnemy(
                CombatCatData.build(
                    combatId = combatId,
                    enemyCat = enemyCat,
                    combatAbilities = combatAbilities
                ),
                onDeath = { catId ->
                    enemyCatDied(catId)
                }
            )
        }
    }

    fun startCombat(){
        _uiState.value = _uiState.value.copy(
            screenState = ScreenState.LOADING,
        )

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    screenState = ScreenState.SUCCESS,
                    combatState = CombatState.IN_PROGRESS,
                    catInitiatives = setupInitialInitiative(),
                )

                startNewTurn()

            } catch(e: Exception){
                _uiState.value = _uiState.value.copy(
                    screenState = ScreenState.FAILURE
                )
            }
        }
    }

    private fun setupInitialInitiative(): List<CatInitiativeData> {
        val catInitiatives = mutableListOf<CatInitiativeData>()
        _uiState.value.teamCombatCats.forEach { cat ->
            catInitiatives.add(
                CatInitiativeData(
                    catId = cat.cat.combatId,
                    image = cat.cat.image,
                    initiative = cat.cat.initiative + Random.nextFloat()
                )
            )
        }
        _uiState.value.enemyCombatCats.forEach { cat ->
            catInitiatives.add(
                CatInitiativeData(
                    catId = cat.cat.combatId,
                    image = cat.cat.image,
                    initiative = cat.cat.initiative + Random.nextFloat()
                )
            )
        }
        return catInitiatives.shuffled().sortedBy { it.initiative }
    }

    private fun addCatToInitiative(cat: CombatCat){
        val initiatives = _uiState.value.catInitiatives.toMutableList()
        initiatives.add(
            CatInitiativeData(
                catId = cat.cat.combatId,
                image = cat.cat.image,
                initiative = cat.cat.initiative
            )
        )
        _uiState.update {
            it.copy(
                catInitiatives = initiatives.sortedBy { initiative -> initiative.initiative }
            )
        }
    }

    private fun enemyCatDied(catId: Int) {
        viewModelScope.launch {
            val remainingCats =
                _uiState.value.enemyCombatCats.filter { cat -> cat.cat.combatId != catId }
            delay(500)
            if (remainingCats.isEmpty()) {
                _uiState.update {
                    it.copy(
                        combatState = CombatState.FINISHED,
                        combatResult = CombatResult.PLAYER_WON
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        enemyCombatCats = remainingCats,
                        catInitiatives = _uiState.value.catInitiatives.filter { cat -> cat.catId != catId },
                    )
                }
            }
        }
    }

    private fun playerCatDied(catId: Int) {
        viewModelScope.launch {
            delay(500)
            val remainingCats =
                _uiState.value.teamCombatCats.filter { cat -> cat.cat.combatId != catId }
            if (remainingCats.isEmpty()) {
                _uiState.update {
                    it.copy(
                        combatState = CombatState.FINISHED,
                        combatResult = CombatResult.PLAYER_LOST
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        teamCombatCats = remainingCats,
                        catInitiatives = _uiState.value.catInitiatives.filter { cat -> cat.catId != catId },
                    )
                }
            }
        }
    }

    fun allyClicked(combatId: Int) {
        when(_uiState.value.combatStage){
            CombatStage.PLAYER_TURN -> {}
            CombatStage.SELECTING_TARGETS, CombatStage.TARGETS_SELECTED -> {
                _uiState.value.activeAbility?.setSelectedAllyCat(combatId)
                _uiState.value.activeAbility?.selectAllyTeam(_uiState.value.teamCombatCats.map { it.cat.combatId })
                if(_uiState.value.activeAbility?.isReady() == true){
                    _uiState.value = _uiState.value.copy(
                        numberOfTurns = _uiState.value.numberOfTurns + 1,
                        combatStage = CombatStage.TARGETS_SELECTED,
                    )
                    useAbility()
                } else {
                    _uiState.value = _uiState.value.copy(
                        numberOfTurns = _uiState.value.numberOfTurns + 1,
                        combatStage = CombatStage.SELECTING_TARGETS,
                    )
                }
            }
            else -> {}
        }
    }

    fun enemyClicked(combatId: Int) {
        when(_uiState.value.combatStage){
            CombatStage.PLAYER_TURN -> {}
            CombatStage.SELECTING_TARGETS, CombatStage.TARGETS_SELECTED -> {
                _uiState.value.activeAbility?.setSelectedEnemyCat(combatId)
                _uiState.value.activeAbility?.selectEnemyTeam(_uiState.value.enemyCombatCats.map { it.cat.combatId })
                if(_uiState.value.activeAbility?.isReady() == true){
                    _uiState.value = _uiState.value.copy(
                        numberOfTurns = _uiState.value.numberOfTurns + 1,
                        combatStage = CombatStage.TARGETS_SELECTED,
                    )
                    useAbility()
                } else {
                    _uiState.value = _uiState.value.copy(
                        numberOfTurns = _uiState.value.numberOfTurns + 1,
                        combatStage = CombatStage.SELECTING_TARGETS,
                    )
                }
            }
            else -> {}
        }
    }

    fun abilityClicked(ability: CombatAbility) {
        if(_uiState.value.activeAbility != null) _uiState.value.activeAbility!!.clear()
        _uiState.value = _uiState.value.copy(
            abilityMessage = if(isAiTurn()) R.string.enemy_turn else ability.getMessage(),
            activeAbility = ability,
            combatStage = CombatStage.SELECTING_TARGETS
        )
    }

    private fun useAbility() {
        val viewModel = this
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                combatStage = CombatStage.ENDING_TURN
            )
            if(_uiState.value.activeAbility != null){
                delay(750)

                _uiState.value.activeAbility!!.apply(viewModel)

                _uiState.value = _uiState.value.copy(
                    abilityMessage = R.string.ending_turn,
                    numberOfTurns = _uiState.value.numberOfTurns + 1
                )

                val activeCat = getCat(_uiState.value.activeCatId)
                activeCat?.reduceAbilitiesCooldown()
                _uiState.value.activeAbility?.applyCooldown()

                delay(750)

                activeCat?.cat?.appliedCombatModifiers?.forEach{ appliedCombatModifier ->
                    when(appliedCombatModifier.combatModifier){
                        CombatModifier.POISONED -> {
                            val damage = (activeCat.cat.maxHealth * appliedCombatModifier.amount).toInt()
                            activeCat.takeDamage(damage, DamageType.POISON)
                            activeCat.cat.lastCombatActionTaken = CombatAction(CombatActionTaken.POISONED, damage, DamageType.POISON)
                            appliedCombatModifier.reduceDuration()
                            _uiState.update {
                                it.copy(
                                    numberOfTurns = it.numberOfTurns + 1
                                )
                            }
                            delay(750)
                        }
                        CombatModifier.STUNNED ->{
                            appliedCombatModifier.reduceDuration()
                        }
                        else -> {}
                    }
                }

                if(_uiState.value.combatState != CombatState.FINISHED) {
                    endTurn()
                }
            }
        }
    }

    private fun catIsInEnemyTeam(activeCatId: Int): Boolean {
        _uiState.value.enemyCombatCats.forEach { cat ->
            if(cat.cat.combatId == activeCatId) return true
        }
        return false
    }

    fun isAiTurn(): Boolean {
        val activeCatId = _uiState.value.activeCatId
        _uiState.value.enemyCombatCats.forEach { cat ->
            if(cat.cat.combatId == activeCatId) return true
        }
        return false
    }

    fun passedTurn(){
        abilityClicked(EmptyAbility(Ability(0)))
        useAbility()
    }

    private fun endTurn() {
        val activeCat = getCat(_uiState.value.activeCatId)
        val abilityUsed = _uiState.value.activeAbility

        if(abilityUsed != null){
            if(activeCat != null){
                val initiativeToAdd = activeCat.cat.getAttackSpeed * abilityUsed.ability.attackSpeedMultiplier
                addInitiativeToCat(activeCat, initiativeToAdd)
                activeCat.reduceBuffsDuration()
                activeCat.refreshBuffModifiers()
            }
            abilityUsed.clear()
        }

        startNewTurn()
    }

    private fun startNewTurn() {
        moveInitiativeBar()
        clearCats()

        val newActiveCat = _uiState.value.catInitiatives.firstOrNull()?.catId
        if(newActiveCat != null){
            val catIsInEnemyTeam = catIsInEnemyTeam(newActiveCat)
            if(catIsInEnemyTeam){
                _uiState.update {
                    it.copy(activeAbility = null,
                        activeCatId = newActiveCat,
                        abilityMessage = R.string.enemy_turn,
                        combatStage = CombatStage.AI_TURN,
                    )
                }
                executeAiTurn()
            } else {
                _uiState.update {
                    it.copy(
                        activeAbility = null,
                        activeCatId = newActiveCat,
                        abilityMessage = R.string.player_turn,
                        combatStage = CombatStage.PLAYER_TURN
                    )
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    screenState = ScreenState.FAILURE
                )
            }
        }
    }

    private fun clearCats() {
        for(cat in _uiState.value.teamCombatCats){
            cat.clear()
        }
        for(cat in _uiState.value.enemyCombatCats){
            cat.clear()
        }
    }

    private fun executeAiTurn() {
        viewModelScope.launch {
            delay(750)

            val enemyCat = _uiState.value.enemyCombatCats.find { it.cat.combatId == _uiState.value.activeCatId }

            if(enemyCat != null){
                if(enemyCat.isStunned()){
                    abilityClicked(EmptyAbility(Ability(0)))
                    useAbility()
                } else {
                    abilityClicked(enemyCat.selectAbility(
                        playerCats = _uiState.value.teamCombatCats.map { it.cat.combatId },
                        enemyCats = _uiState.value.enemyCombatCats.map { it.cat.combatId },
                        )
                    )
                    val ability = _uiState.value.activeAbility
                    if(ability != null){
                        useAbility()
                    }
                }
            }
        }
    }

    private fun moveInitiativeBar() {
        val initiatives = _uiState.value.catInitiatives
        val firstInitiativeTime = initiatives.firstOrNull()?.initiative ?: 0.0f
        initiatives.forEach { initiative ->
            initiative.initiative -= firstInitiativeTime
            if(initiative.initiative < 0) initiative.initiative = 0.0f
        }
        _uiState.update {
            it.copy(
                catInitiatives = initiatives.sortedBy { initiative -> initiative.initiative }
            )
        }
    }

    private fun addInitiativeToCat(combatCat: CombatCat, initiativeToAdd: Float){
        val initiatives = _uiState.value.catInitiatives
        val catInitiative = initiatives.find { it.catId == combatCat.cat.combatId }
        if(catInitiative != null){
            if(combatCat.isSlowed()){
                catInitiative.initiative += combatCat.getSlowAmount()
                combatCat.reduceSlowDuration()
            }
            catInitiative.initiative += initiativeToAdd
        }
        _uiState.update {
            it.copy(
                catInitiatives = initiatives.sortedBy { initiative -> initiative.initiative }
            )
        }
    }

    fun damageCat(selectedCatId: Int, ability: Ability) {
        val cat = getCat(selectedCatId)
        if(cat != null){
            if(ability.damageType != DamageType.POISON && cat.isShielded()){
                cat.shieldAbsorbedDamage()
                cat.cat.lastCombatActionTaken = CombatAction(CombatActionTaken.SHIELDED, 0, ability.damageType)
                return
            }
            var damage = ((getCat(_uiState.value.activeCatId)?.cat?.getAttack ?: 0f) * ability.damageMultiplier) - cat.cat.getDefense
            if(damage <= 0) damage = 1f
            cat.takeDamage(damage.toInt(), ability.damageType)
            cat.cat.lastCombatActionTaken = CombatAction(CombatActionTaken.DAMAGED, damage.toInt(), ability.damageType)
        }
    }

    fun healCat(catId: Int, ability: Ability) {
        val cat = getCat(catId)
        if(cat != null){
            val damage = (getCat(_uiState.value.activeCatId)?.cat?.getAttack ?: 0f) * ability.damageMultiplier
            cat.heal(damage.toInt(), ability.damageType)
            cat.cat.lastCombatActionTaken = CombatAction(CombatActionTaken.HEALED, damage.toInt(), ability.damageType)
        }
    }

    fun applyModifiers(catId: Int, ability: Ability) {
        val selectedCat = getCat(_uiState.value.activeCatId)
        val cat = getCat(catId)
        if(cat != null){
            when(ability.combatModifier){
                CombatModifier.SLOWED -> {
                    cat.addCombatModifier(
                        appliedCombatModifier = appliedCombatModifier(
                            combatModifier = CombatModifier.SLOWED,
                            amount = ability.combatModifierValue,
                            duration = ability.combatModifierDuration
                        )
                    )
                }
                CombatModifier.STUNNED -> {
                    cat.addCombatModifier(
                        appliedCombatModifier = appliedCombatModifier(
                            combatModifier = CombatModifier.STUNNED,
                            duration = ability.combatModifierDuration
                        )
                    )
                }
                CombatModifier.POISONED -> {
                    cat.addCombatModifier(
                        appliedCombatModifier = appliedCombatModifier(
                            combatModifier = CombatModifier.POISONED,
                            amount = ability.combatModifierValue,
                            duration = ability.combatModifierDuration
                        )
                    )
                }
                CombatModifier.CLEANSED -> {}
                CombatModifier.SHIELDED -> {
                    cat.addCombatModifier(
                        appliedCombatModifier = appliedCombatModifier(
                            combatModifier = CombatModifier.SHIELDED,
                            duration = ability.combatModifierDuration
                        )
                    )
                }
                null -> {}
                else -> {
                    cat.addCombatModifier(
                        appliedCombatModifier = appliedCombatModifier(
                            combatModifier = ability.combatModifier,
                            amount = if(selectedCat != null){
                                ability.combatModifierValue + (selectedCat.cat.getAttack * ability.damageMultiplier)
                            } else {
                                ability.combatModifierValue
                            },
                            duration = ability.combatModifierDuration
                        )
                    )
                    cat.refreshBuffModifiers()
                }
            }
        }
    }

    fun summonCatForCurrentTeam(summonId: Long) {
        viewModelScope.launch {
            val cat = enemyCatRepository.getEnemyCatById(summonId)
            val combatCat = createEnemyCombatCat(cat, getHighestCombatId() + 1)
            addEnemyToTeam(combatCat)
        }
    }

    private fun enemyTeamNotFull(): Boolean {
        return _uiState.value.enemyCombatCats.size < 4
    }

    fun cloneEnemy() {
        if(enemyTeamNotFull()){
            val currentEnemy = getEnemyCat(_uiState.value.activeCatId)
            if(currentEnemy != null){
                val combatData = CombatCatData.copy(getHighestCombatId() + 1, currentEnemy.cat)
                val newCat = when(currentEnemy){
                    is SimpleEnemy -> SimpleEnemy(combatData, onDeath = currentEnemy.onDeath)
                    is SummonerEnemy -> SummonerEnemy(combatData, onDeath = currentEnemy.onDeath)
                    is UniqueSummonEnemy -> UniqueSummonEnemy(combatData, onDeath = currentEnemy.onDeath)
                }
                addEnemyToTeam(newCat)
            }
        }
    }

    private fun getEnemyCat(catId: Int): EnemyCombatCat? = _uiState.value.enemyCombatCats.find { catId == it.cat.combatId }

    private fun getHighestCombatId(): Int {
        var highestCombatId = 0
        (_uiState.value.teamCombatCats + _uiState.value.enemyCombatCats).forEach{
            if(highestCombatId < it.cat.combatId) highestCombatId = it.cat.combatId
        }
        return highestCombatId
    }

    private fun addEnemyToTeam(enemyCombatCat: EnemyCombatCat) {
        val enemyCats = _uiState.value.enemyCombatCats.toMutableList()
        val initiatives = _uiState.value.catInitiatives.toMutableList()
        enemyCats.add(enemyCombatCat)
        initiatives.add(
            CatInitiativeData(
                catId = enemyCombatCat.cat.combatId,
                image = enemyCombatCat.cat.image,
                initiative = enemyCombatCat.cat.initiative + Random.nextFloat()
            )
        )
        _uiState.update {
            it.copy(
                enemyCombatCats = enemyCats,
                catInitiatives = initiatives.sortedBy { initiative -> initiative.initiative },
            )
        }
    }
}

