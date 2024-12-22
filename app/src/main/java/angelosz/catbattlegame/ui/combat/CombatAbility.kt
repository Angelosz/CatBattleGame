package angelosz.catbattlegame.ui.combat

import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.models.entities.Ability

sealed class CombatAbility(){
    private var cooldown = 0

    abstract val ability: Ability
    abstract fun setSelectedEnemyCat(catCombatId: Int)
    abstract fun setSelectedAllyCat(catCombatId: Int)
    abstract fun isReady(): Boolean
    abstract fun apply(viewModel: CombatScreenViewModel)
    abstract fun getSelectedCatsIds(): List<Int>
    abstract fun selectEnemyTeam(enemyTeam: List<Int>)
    abstract fun selectAllyTeam(allyTeam: List<Int>)
    abstract fun clear()

    fun onCooldown(): Boolean = cooldown > 0

    fun applyCooldown() {
        cooldown += ability.cooldown
    }

    fun reduceCooldown(amount: Int) {
        cooldown -= amount
        if(cooldown < 0) cooldown = 0
    }

    companion object Builder{
        fun build(abilities: List<Ability>): List<CombatAbility>{
            return abilities.map{ ability ->
                when(Pair(ability.abilityType, ability.targets)){
                    Pair(AbilityType.DAMAGE, AbilityTarget.SINGLE_ENEMY) -> SingleTargetDamageAbility(ability)
                    Pair(AbilityType.HEALING, AbilityTarget.ALLY) -> SingleTargetHealingAbility(ability)
                    Pair(AbilityType.HEALING, AbilityTarget.TEAM) -> AoEHealAbility(ability)
                    Pair(AbilityType.DAMAGE, AbilityTarget.ENEMY_TEAM) -> AoEDamageAbility(ability)
                    else -> EmptyAbility(ability)
                }
            }
        }
    }
}

class EmptyAbility(override val ability: Ability) : CombatAbility() {
    override fun setSelectedEnemyCat(catCombatId: Int) {}
    override fun setSelectedAllyCat(catCombatId: Int) {}
    override fun isReady(): Boolean = false
    override fun apply(viewModel: CombatScreenViewModel) {}
    override fun getSelectedCatsIds(): List<Int> = emptyList()
    override fun selectEnemyTeam(enemyTeam: List<Int>) {}
    override fun selectAllyTeam(allyTeam: List<Int>) {}

    override fun clear() {}
}

class SingleTargetDamageAbility(override val ability: Ability): CombatAbility(){
    private var selectedCatId: Int? = null

    override fun setSelectedEnemyCat(catCombatId: Int) {
        selectedCatId =
            if(selectedCatId != catCombatId){
                catCombatId
            } else {
                null
            }
    }

    override fun setSelectedAllyCat(catCombatId: Int) { }

    override fun isReady(): Boolean {
        return selectedCatId != null
    }

    override fun apply(viewModel: CombatScreenViewModel) {
        val catId = selectedCatId
        if(catId != null) {
            viewModel.damageCat(catId, ability)
        }
    }

    override fun getSelectedCatsIds(): List<Int> {
        val catId = selectedCatId
        if(catId != null)
            return listOf(catId)
        return emptyList()
    }

    override fun selectEnemyTeam(enemyTeam: List<Int>) {}

    override fun selectAllyTeam(allyTeam: List<Int>) {}

    override fun clear() {
        selectedCatId = null
    }
}

class SingleTargetHealingAbility(override val ability: Ability): CombatAbility(){
    private var selectedCatId: Int? = null

    override fun setSelectedEnemyCat(catCombatId: Int) { }

    override fun setSelectedAllyCat(catCombatId: Int) {
        selectedCatId =
            if(selectedCatId != catCombatId){
                catCombatId
            } else {
                null
            }
    }

    override fun isReady(): Boolean {
        return selectedCatId != null
    }

    override fun apply(viewModel: CombatScreenViewModel) {
        val catId = selectedCatId
        if(catId != null) {
            viewModel.healCat(catId, ability)
        }
    }

    override fun getSelectedCatsIds(): List<Int> {
        val catId = selectedCatId
        if(catId != null)
            return listOf(catId)
        return emptyList()

    }

    override fun selectEnemyTeam(enemyTeam: List<Int>) {}

    override fun selectAllyTeam(allyTeam: List<Int>) {}

    override fun clear() {
        selectedCatId = null
    }
}

class AoEDamageAbility(override val ability: Ability): CombatAbility(){
    private var selectedCats: List<Int> = listOf()

    override fun setSelectedEnemyCat(catCombatId: Int) { }
    override fun setSelectedAllyCat(catCombatId: Int) { }
    override fun selectAllyTeam(allyTeam: List<Int>) {}

    override fun selectEnemyTeam(enemyTeam: List<Int>) {
        if(selectedCats.isEmpty()){
            selectedCats = enemyTeam
        } else {
            selectedCats = emptyList()
        }
    }

    override fun getSelectedCatsIds(): List<Int> {
        return selectedCats
    }

    override fun isReady(): Boolean {
        return selectedCats.isNotEmpty()
    }

    override fun apply(viewModel: CombatScreenViewModel) {
        selectedCats.forEach { catId ->
            viewModel.damageCat(catId, ability)
        }
    }

    override fun clear() {
        selectedCats = emptyList()
    }
}
class AoEHealAbility(override val ability: Ability): CombatAbility(){
    private var selectedCats: List<Int> = listOf()

    override fun setSelectedEnemyCat(catCombatId: Int) { }
    override fun setSelectedAllyCat(catCombatId: Int) { }
    override fun selectEnemyTeam(enemyTeam: List<Int>) { }
    override fun selectAllyTeam(allyTeam: List<Int>) {
        if(selectedCats.isEmpty()){
            selectedCats = allyTeam
        } else {
            selectedCats = emptyList()
        }
    }


    override fun getSelectedCatsIds(): List<Int> {
        return selectedCats
    }

    override fun isReady(): Boolean {
        return selectedCats.isNotEmpty()
    }

    override fun apply(viewModel: CombatScreenViewModel) {
        selectedCats.forEach { catId ->
            viewModel.healCat(catId, ability)
        }
    }

    override fun clear() {
        selectedCats = emptyList()
    }
}