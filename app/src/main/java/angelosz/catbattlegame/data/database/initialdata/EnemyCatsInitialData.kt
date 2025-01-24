package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.campaign.EnemyType

class EnemyCatsInitialData {
    val enemyCats = listOf(
        EnemyCat(
            id = 1,
            name = "Wool Ball",
            baseHealth = 60f,
            baseAttack = 18f,
            baseDefense = 1.2f,
            attackSpeed = 1.25f,
            image = R.drawable.enemy_wool_ball_300
        ),
        EnemyCat(
            id = 2,
            name = "Mouse Thief",
            baseHealth = 45f,
            baseAttack = 11f,
            baseDefense = 1f,
            attackSpeed = 0.9f,
            image = R.drawable.enemy_mouse_thief_300
        ),
        EnemyCat(
            id = 3,
            name = "Playful Puppy",
            baseHealth = 100f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_puppy_300
        ),
        EnemyCat(
            id = 4,
            name = "Sparrow Scout",
            baseHealth = 120f,
            baseAttack = 18f,
            baseDefense = 1.2f,
            attackSpeed = 1f,
            image = R.drawable.enemy_sparrow_apprentice_guard_300
        ),
        EnemyCat(
            id = 5,
            name = "Sparrow Guard",
            baseHealth = 140f,
            baseAttack = 26f,
            baseDefense = 2.5f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_sparrow_guard_300
        ),
        EnemyCat(
            id = 6,
            name = "Raven Guard",
            baseHealth = 140f,
            baseAttack = 22f,
            baseDefense = 1f,
            attackSpeed = 1.1f,
            image = R.drawable.enemy_raven_guard_300
        ),
        EnemyCat(
            id = 7,
            name = "Angry Garden Gnome",
            baseHealth = 60f,
            baseAttack = 14f,
            baseDefense = 1f,
            attackSpeed = 1.2f,
            image = R.drawable.enemy_angry_gnome_300
        ),
        EnemyCat(
            id = 8,
            name = "Jumping Spider",
            baseHealth = 40f,
            baseAttack = 12f,
            baseDefense = 1f,
            attackSpeed = 1f,
            image = R.drawable.enemy_jumping_spider_300
        ),
        EnemyCat(
            id = 9,
            name = "Lawn Mower",
            baseHealth = 100f,
            baseAttack = 22f,
            baseDefense = 2f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_lawn_mower_300
        ),
        EnemyCat(
            id = 10,
            name = "Hose",
            baseHealth = 100f,
            baseAttack = 16f,
            baseDefense = 1f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_hose_300
        ),
        EnemyCat(
            id = 11,
            name = "Ant Worker",
            baseHealth = 30f,
            baseAttack = 12f,
            baseDefense = 0.8f,
            attackSpeed = 1f,
            image = R.drawable.enemy_worker_ant_300
        ),
        EnemyCat(
            id = 12,
            name = "Ant Warrior",
            baseHealth = 55f,
            baseAttack = 16f,
            baseDefense = 2.5f,
            attackSpeed = 1.1f,
            image = R.drawable.enemy_warrior_ant_300
        ),
        EnemyCat(
            id = 13,
            name = "Angry Snail",
            baseHealth = 85f,
            baseAttack = 18f,
            baseDefense = 2.5f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_angry_snail_300
        ),
        EnemyCat(
            id = 14,
            name = "Happy Snail",
            baseHealth = 85f,
            baseAttack = 16f,
            baseDefense = 2.5f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_happy_snail_300
        ),
        EnemyCat(
            id = 15,
            name = "Mosquito",
            baseHealth = 50f,
            baseAttack = 12f,
            baseDefense = 1.5f,
            attackSpeed = 1.3f,
            enemyType = EnemyType.SUMMONER,
            image = R.drawable.enemy_mosquito_300
        ),
        EnemyCat(
            id = 16,
            name = "Hedgehog",
            baseHealth = 200f,
            baseAttack = 24f,
            baseDefense = 2.5f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_guardian_hedgehog_300
        ),
        EnemyCat(
            id = 17,
            name = "Young Racoon",
            baseHealth = 60f,
            baseAttack = 20f,
            baseDefense = 1f,
            attackSpeed = 2.5f,
            enemyType = EnemyType.UNIQUE_SUMMONER,
            image = R.drawable.enemy_young_racoon_300
        ),
        EnemyCat(
            id = 18,
            name = "Mother Racoon",
            baseHealth = 200f,
            baseAttack = 28f,
            baseDefense = 2f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_mother_racoon_300
        ),
        EnemyCat(
            id = 19,
            name = "Dark Rogue",
            baseHealth = 200f,
            baseAttack = 28f,
            baseDefense = 2f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_dark_rogue_300
        ),
        EnemyCat(
            id = 20,
            name = "Ant Queen",
            baseHealth = 200f,
            baseAttack = 28f,
            baseDefense = 2f,
            attackSpeed = 2.5f,
            enemyType = EnemyType.SUMMONER,
            image = R.drawable.enemy_queen_ant_300
        ),
        EnemyCat(
            id = 21,
            name = "Hunter Gnome",
            baseHealth = 120f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.4f,
            enemyType = EnemyType.UNIQUE_SUMMONER,
            image = R.drawable.enemy_hunter_gnome_300
        ),
    )

    private val enemyAbilities: Map<Long, List<Int>> = mapOf(
        1L to listOf(11, 12),           // Wool Ball
        2L to listOf(1, 13),            // Mouse Thief
        3L to listOf(10, 11, 12),       // Playful Puppy
        4L to listOf(1, 4, 13),         // Sparrow Scout
        5L to listOf(13, 14, 11),       // Sparrow Guard
        6L to listOf(11, 3, 2, 10),     // Raven Guard
        7L to listOf(1, 11, 18),        // Angry gnome
        8L to listOf(1, 13),            // Jumping spider
        9L to listOf(11, 10),           // Lawn mower
        10L to listOf(19),              // Hose
        11L to listOf(1),               // Ant worker
        12L to listOf(11, 10),          // Ant warrior
        13L to listOf(11, 20),          // Angry snail
        14L to listOf(11, 20),          // Happy Snail
        15L to listOf(1, 13, 21),       // Mosquito
        16L to listOf(11, 12, 10, 2),   // Hedgehog
        17L to listOf(22, 2, 9, 3),     // Young racoon
        18L to listOf(11, 10, 12, 17),  // Mother racoon
        19L to listOf(13, 4, 14, 20),   // Dark rogue
        20L to listOf(23, 24, 19, 20),  // Ant queen
        21L to listOf(8, 20, 3, 11),    // Hunter gnome
    )

    fun getEnemyAbilities(): List<EnemyAbility> =
        enemyAbilities.flatMap { (catId, abilityIds) ->
            abilityIds.map { abilityId ->
                EnemyAbility(catId, abilityId)
            }
        }
}