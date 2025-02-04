package angelosz.catbattlegame.utils

import angelosz.catbattlegame.domain.enums.CatRarity

object GameConstants {
    const val GAME_VERSION = 2

    const val MAX_CAT_LEVEL = 30
    const val EXPERIENCE_PER_LEVEL = 400

    const val KITTEN_DISENCHANT_VALUE = 25
    const val KITTEN_CRYSTAL_COST = 100
    const val KITTEN_BATTLECHEST_COST = 100
    const val TEEN_DISENCHANT_VALUE = 100
    const val TEEN_CRYSTAL_COST = 400
    const val TEEN_BATTLECHEST_COST = 400
    const val ADULT_DISENCHANT_VALUE = 250
    const val ADULT_CRYSTAL_COST = 1000
    const val ADULT_BATTLECHEST_COST = 1000
    const val ELDER_DISENCHANT_VALUE = 800
    const val ELDER_CRYSTAL_COST = 3600
    const val ELDER_BATTLECHEST_COST = 3600

    val GET_CAT_DISENCHANT_VALUE: (CatRarity) -> Int = {
        when(it){
            CatRarity.KITTEN -> KITTEN_DISENCHANT_VALUE
            CatRarity.TEEN -> TEEN_DISENCHANT_VALUE
            CatRarity.ADULT -> ADULT_DISENCHANT_VALUE
            CatRarity.ELDER -> ELDER_DISENCHANT_VALUE
        }
    }

    val GET_CAT_CRYSTAL_COST: (CatRarity) -> Int = {
        when(it){
            CatRarity.KITTEN -> KITTEN_CRYSTAL_COST
            CatRarity.TEEN -> TEEN_CRYSTAL_COST
            CatRarity.ADULT -> ADULT_CRYSTAL_COST
            CatRarity.ELDER -> ELDER_CRYSTAL_COST
        }
    }

    val GET_BATTLECHEST_GOLD_COST: (CatRarity) -> Int = {
        when(it){
            CatRarity.KITTEN -> KITTEN_BATTLECHEST_COST
            CatRarity.TEEN -> TEEN_BATTLECHEST_COST
            CatRarity.ADULT -> ADULT_BATTLECHEST_COST
            CatRarity.ELDER -> ELDER_BATTLECHEST_COST
        }
    }
}