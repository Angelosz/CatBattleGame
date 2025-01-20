package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat

class EnemyCatsInitialData {
    val enemyCats = listOf(
        EnemyCat(id = 1,
            name = "Wool Ball",
            baseHealth = 60f,
            baseAttack = 18f,
            baseDefense = 1.2f,
            attackSpeed = 1.25f,
            image = R.drawable.enemy_wool_ball_300
        ),
        EnemyCat(id = 2,
            name = "Mouse Thief",
            baseHealth = 45f,
            baseAttack = 11f,
            baseDefense = 1f,
            attackSpeed = 0.9f,
            image = R.drawable.enemy_mouse_thief_300),
        EnemyCat(id = 3,
            name = "Playful Puppy",
            baseHealth = 120f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_puppy_300),
        EnemyCat(id = 4, name = "Sparrow Scout",
            baseHealth = 1f, image = R.drawable.enemy_sparrow_apprentice_guard_300),
        EnemyCat(id = 5, name = "Sparrow Guard",
            baseHealth = 1f, image = R.drawable.enemy_sparrow_guard_300),
        EnemyCat(id = 6, name = "Raven Guard",
            baseHealth = 1f, image = R.drawable.enemy_raven_guard_300),
        EnemyCat(id = 7, name = "Angry Garden Gnome",
            baseHealth = 1f, image = R.drawable.enemy_angry_gnome_300),
        EnemyCat(id = 8, name = "Jumping Spider",
            baseHealth = 1f, image = R.drawable.enemy_jumping_spider_300),
        EnemyCat(id = 9, name = "Lawn Mower",
            baseHealth = 1f, image = R.drawable.enemy_lawn_mower_300),
        EnemyCat(id = 10, name = "Hose",
            baseHealth = 1f, image = R.drawable.enemy_hose_300),
        EnemyCat(id = 11, name = "Ant Worker",
            baseHealth = 1f, image = R.drawable.enemy_worker_ant_300),
        EnemyCat(id = 12, name = "Ant Warrior",
            baseHealth = 1f, image = R.drawable.enemy_warrior_ant_300),
        EnemyCat(id = 13, name = "Angry Snail",
            baseHealth = 1f, image = R.drawable.enemy_angry_snail_300),
        EnemyCat(id = 14, name = "Happy Snail",
            baseHealth = 1f, image = R.drawable.enemy_happy_snail_300),
        EnemyCat(id = 15, name = "Mosquito",
            baseHealth = 1f, image = R.drawable.enemy_mosquito_300),
        EnemyCat(id = 16, name = "Hedgehog",
            baseHealth = 1f, image = R.drawable.enemy_guardian_hedgehog_300),
        EnemyCat(id = 17, name = "Young Racoon",
            baseHealth = 1f, image = R.drawable.enemy_young_racoon_300),
        EnemyCat(id = 18, name = "Mother Racoon",
            baseHealth = 1f, image = R.drawable.enemy_mother_racoon_300),
        EnemyCat(id = 19, name = "Dark Rogue",
            baseHealth = 1f, image = R.drawable.enemy_dark_rogue_300),
        EnemyCat(id = 20, name = "Ant Queen",
            baseHealth = 1f, image = R.drawable.enemy_queen_ant_300),
        EnemyCat(id = 21, name = "Hunter Gnome",
            baseHealth = 1f, image = R.drawable.enemy_hunter_gnome_300),
    )

    private val enemyAbilities: Map<Long, List<Int>> = mapOf(
        1L to listOf(11, 12),
        2L to listOf(1),
        3L to listOf(10, 11, 12),
        4L to listOf(1),
        5L to listOf(1),
        6L to listOf(1),
        7L to listOf(1),
        8L to listOf(1),
        9L to listOf(1),
        10L to listOf(1),
        11L to listOf(1),
        12L to listOf(1),
        13L to listOf(1),
        14L to listOf(1),
        15L to listOf(1),
        16L to listOf(1),
        17L to listOf(1),
        18L to listOf(1),
        19L to listOf(1)
    )

    fun getEnemyAbilities(): List<EnemyAbility> =
        enemyAbilities.flatMap { (catId, abilityIds) ->
            abilityIds.map { abilityId ->
                EnemyAbility(catId, abilityId)
            }
        }
}