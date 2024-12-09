package angelosz.catbattlegame.ui.battlechests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BattleChestsViewModel(
    private val catRepository: CatRepository,
    private val playerAccountRepository: PlayerAccountRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<BattleChestsUiState> =
        MutableStateFlow(BattleChestsUiState())
    val uiState: StateFlow<BattleChestsUiState> = _uiState

    init {
        viewModelScope.launch {
            fetchBattleChests()
        }
    }

    private suspend fun fetchBattleChests() {
        val storedBattleChests = playerAccountRepository.getAllBattleChests()
        val battleChests = mutableMapOf<Pair<CatRarity, BattleChestType>, List<BattleChest>>()

        //transform list to Map<Pair<CatRarity, BattleChestType>, Int>
        battleChests.putAll(storedBattleChests
            .groupBy { it.rarity to it.type }
        )

        // Get first battlechest if only 1 is available
        var selectedBattleChest: BattleChest? = null
        if(storedBattleChests.size == 1) {
            selectedBattleChest = storedBattleChests.first()
        }

        _uiState.update {
            it.copy(
                battleChests = battleChests,
                selectedBattleChest = selectedBattleChest
            )
        }
    }

    fun openSelectedBattleChest(){
        viewModelScope.launch {
            val battleChest = uiState.value.selectedBattleChest
            when(battleChest?.type){
                BattleChestType.NORMAL -> {
                    _uiState.update {
                        it.copy(catReward = getRandomCat(battleChest.rarity))
                    }
                }
                BattleChestType.NEW_CAT -> {
                    val cat = getRandomUnownedCatOfRarity(battleChest.rarity)
                    addCatToPlayerAccount(cat.id)
                    deleteBattleChest(battleChest)
                    fetchBattleChests()
                    _uiState.update {
                        it.copy(
                            catReward = cat
                        )
                    }
                }
                null -> { }
            }
        }
    }

    private suspend fun getRandomCat(rarity: CatRarity): Cat {
        return catRepository.getRandomCatByRarity(rarity)
    }

    private suspend fun getRandomUnownedCatOfRarity(rarity: CatRarity): Cat {
        val unownedCatsIds = catRepository.getUnownedCatsOfRarityIds(rarity)
        return if(unownedCatsIds.isNotEmpty()){
            catRepository.getCatById(unownedCatsIds.random())
        } else {
            getRandomCat(rarity)
        }
    }

    fun clearCatReward(){
        _uiState.update {
            it.copy( catReward = null )
        }
    }

    private suspend fun addCatToPlayerAccount(catId: Int){
        if(!playerAccountRepository.ownsCat(catId)){
            playerAccountRepository.insertOwnedCat(
                OwnedCat(
                    catId = catId,
                    level = 1,
                    experience = 0
                )
            )
        }
    }

    private suspend fun deleteBattleChest(battleChest: BattleChest){
        playerAccountRepository.deleteBattleChest(battleChest)
    }


    fun countBattleChests(): Int {
        var count = 0
        for ( battleChest in _uiState.value.battleChests ){
            count += battleChest.value.size
        }
        return count
    }

    fun selectBattleChest(battleChest: BattleChest) {
        _uiState.update {
            it.copy(
                selectedBattleChest = battleChest
            )
        }
    }
}