package angelosz.catbattlegame.ui.encyclopedia

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.ui.collections.CollectionView
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.CatDetailsData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class EncyclopediaViewModelTest {

    private lateinit var viewModel: EncyclopediaViewModel
    private lateinit var cats: List<Cat>
    private lateinit var abilities: List<Ability>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore(){
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = EncyclopediaViewModel(
            CatRepositoryForEncyclopediaTest(),
            AbilityRepositoryForEncyclopediaTest()
        )

        cats = listOf(
            Cat(id = 0), Cat(id = 1)
        )
        abilities = listOf(
            Ability(0), Ability(1)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `data is fetched correctly at setup`() = runTest {
        val firstCat = cats.first()
        val firstAbility = abilities.first()
        val uiState = EncyclopediaUiState(
            cats = cats,
            abilities = abilities,
            selectedCatData = CatDetailsData(
                cat = firstCat,
                abilities = emptyList(),
                isElderOf = "",
                nextEvolutionName = ""
            ),
            selectedAbility = firstAbility,
            onDetailsView = false,
            collectionView = CollectionView.CATS,
            screenState = ScreenState.SUCCESS
        )

        assertEquals(uiState, viewModel.uiState.value)
    }

    @Test
    fun `all cats are fetched correctly`() = runTest {
       assertEquals(cats, viewModel.uiState.value.cats)
    }

    @Test
    fun `all abilities are fetched correctly`() = runTest {
        assertEquals(abilities, viewModel.uiState.value.abilities)
    }

    @Test
    fun `cat details are fetched correctly`() = runTest {
        val cat = CatDetailsData(
            cat = Cat(id = 1),
            abilities = emptyList(),
            isElderOf = "",
            nextEvolutionName = ""
        )

        viewModel.updateSelectedCat(1)
        testScheduler.advanceUntilIdle()

        assertEquals(cat, viewModel.uiState.value.selectedCatData)
    }

    @Test
    fun `uistate state is set to Failure when selecting cat with wrong id`() = runTest {
        viewModel.updateSelectedCat(2)

        testScheduler.advanceUntilIdle()

        assertEquals(ScreenState.FAILURE, viewModel.uiState.value.screenState)
    }

    @Test
    fun `ability details are fetched correctly`() = runTest {
        val ability = Ability(1)

        viewModel.updateSelectedAbility(1)
        testScheduler.advanceUntilIdle()

        assertEquals(ability, viewModel.uiState.value.selectedAbility)
    }

    @Test
    fun `uistate state is set to Failure when selecting ability with wrong id`() = runTest {
        viewModel.updateSelectedAbility(2)
        testScheduler.advanceUntilIdle()

        assertEquals(ScreenState.FAILURE, viewModel.uiState.value.screenState)
    }

    @Test
    fun `change view correctly`() = runTest {
        viewModel.changeView(true)

        assertEquals(true, viewModel.uiState.value.onDetailsView)
    }

    @Test
    fun `update collection view correctly`() = runTest {
        viewModel.updateCollectionView(CollectionView.ABILITIES)

        assertEquals(CollectionView.ABILITIES, viewModel.uiState.value.collectionView)
    }
}



private class AbilityRepositoryForEncyclopediaTest : AbilityRepository {
    override suspend fun getAllPlayerAbilities(): List<Ability> = listOf(
        Ability(0), Ability(1)
    )
    override suspend fun getAbilityById(id: Int): Ability {
        val abilities = getAllPlayerAbilities()
        for(ability in abilities){
            if(ability.id == id){
                return ability
            }
        }
        throw NullPointerException()
    }
    override suspend fun insertAbilities(abilities: List<Ability>) {}
    override suspend fun getCatAbilities(catId: Int): List<Ability> {
        val cats = listOf(
            Cat(id = 0), Cat(id = 1)
        )
        val cat = cats.find { it.id == catId }
        if(cat != null){
            return emptyList()
        }
        throw NullPointerException()
    }
    override suspend fun getEnemyCatAbilities(catId: Int): List<Ability> = emptyList()
}

private class CatRepositoryForEncyclopediaTest: CatRepository {
    override suspend fun getAllCats(): List<Cat> = listOf(
        Cat(id = 0), Cat(id = 1)
    )
    override suspend fun getCatById(id: Int): Cat {
        val cats = getAllCats()
        for (cat in cats) {
            if (cat.id == id) {
                return cat
            }
        }
        throw NullPointerException()
    }
    override suspend fun insertCats(cats: List<Cat>) {}
    override suspend fun getCatsById(ids: List<Int>): List<Cat> = emptyList()
    override suspend fun getRandomCatByRarity(rarity: CatRarity): Cat = Cat()
    override suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int> = emptyList()
}

