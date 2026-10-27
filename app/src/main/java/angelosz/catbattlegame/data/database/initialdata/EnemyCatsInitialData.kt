package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.campaign.EnemyType

class EnemyCatsInitialData {
    val enemyCats = listOf(
        EnemyCat(
            id = 1,
            name = R.string.enemy_wool_ball,
            description = R.string.enemy_wool_ball_desc,
            baseHealth = 50f,
            baseAttack = 16f,
            baseDefense = 1.2f,
            attackSpeed = 1.25f,
            image = R.drawable.enemy_wool_ball_300
        ),
        EnemyCat(
            id = 2,
            name = R.string.enemy_mouse_thief,
            description = R.string.enemy_mouse_thief_desc,
            baseHealth = 50f,
            baseAttack = 12f,
            baseDefense = 1f,
            attackSpeed = 0.9f,
            image = R.drawable.enemy_mouse_thief_300
        ),
        EnemyCat(
            id = 3,
            name = R.string.enemy_playful_puppy,
            description = R.string.enemy_playful_puppy_desc,
            baseHealth = 100f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_puppy_300
        ),
        EnemyCat(
            id = 4,
            name = R.string.enemy_sparrow_scout,
            description = R.string.enemy_sparrow_scout_desc,
            baseHealth = 120f,
            baseAttack = 18f,
            baseDefense = 1.2f,
            attackSpeed = 1f,
            image = R.drawable.enemy_sparrow_apprentice_guard_300
        ),
        EnemyCat(
            id = 5,
            name = R.string.enemy_sparrow_guard,
            description = R.string.enemy_sparrow_guard_desc,
            baseHealth = 160f,
            baseAttack = 26f,
            baseDefense = 3.5f,
            attackSpeed = 1f,
            image = R.drawable.enemy_sparrow_guard_300
        ),
        EnemyCat(
            id = 6,
            name = R.string.enemy_raven_guard,
            description = R.string.enemy_raven_guard_desc,
            baseHealth = 160f,
            baseAttack = 22f,
            baseDefense = 3.5f,
            attackSpeed = 1f,
            image = R.drawable.enemy_raven_guard_300
        ),
        EnemyCat(
            id = 7,
            name = R.string.enemy_angry_garden_gnome,
            description = R.string.enemy_angry_garden_gnome_desc,
            baseHealth = 60f,
            baseAttack = 14f,
            baseDefense = 1f,
            attackSpeed = 1.2f,
            image = R.drawable.enemy_angry_gnome_300
        ),
        EnemyCat(
            id = 8,
            name = R.string.enemy_jumping_spider,
            description = R.string.enemy_jumping_spider_desc,
            baseHealth = 45f,
            baseAttack = 14f,
            baseDefense = 1.5f,
            attackSpeed = 1f,
            image = R.drawable.enemy_jumping_spider_300
        ),
        EnemyCat(
            id = 9,
            name = R.string.enemy_lawn_mower,
            description = R.string.enemy_lawn_mower_desc,
            baseHealth = 100f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_lawn_mower_300
        ),
        EnemyCat(
            id = 10,
            name = R.string.enemy_hose,
            description = R.string.enemy_hose_desc,
            baseHealth = 100f,
            baseAttack = 14f,
            baseDefense = 1f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_hose_300
        ),
        EnemyCat(
            id = 11,
            name = R.string.enemy_ant_worker,
            description = R.string.enemy_ant_worker_desc,
            baseHealth = 30f,
            baseAttack = 14f,
            baseDefense = 1f,
            attackSpeed = 1f,
            image = R.drawable.enemy_worker_ant_300
        ),
        EnemyCat(
            id = 12,
            name = R.string.enemy_ant_warrior,
            description = R.string.enemy_ant_warrior_desc,
            baseHealth = 55f,
            baseAttack = 18f,
            baseDefense = 2.5f,
            attackSpeed = 1.1f,
            image = R.drawable.enemy_warrior_ant_300
        ),
        EnemyCat(
            id = 13,
            name = R.string.enemy_angry_snail,
            description = R.string.enemy_angry_snail_desc,
            baseHealth = 85f,
            baseAttack = 18f,
            baseDefense = 2.5f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_angry_snail_300
        ),
        EnemyCat(
            id = 14,
            name = R.string.enemy_happy_snail,
            description = R.string.enemy_happy_snail_desc,
            baseHealth = 85f,
            baseAttack = 16f,
            baseDefense = 2.5f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_happy_snail_300
        ),
        EnemyCat(
            id = 15,
            name = R.string.enemy_mosquito,
            description = R.string.enemy_mosquito_desc,
            baseHealth = 70f,
            baseAttack = 18f,
            baseDefense = 1f,
            attackSpeed = 1f,
            enemyType = EnemyType.SUMMONER,
            image = R.drawable.enemy_mosquito_300
        ),
        EnemyCat(
            id = 16,
            name = R.string.enemy_hedgehog,
            description = R.string.enemy_hedgehog_desc,
            baseHealth = 280f,
            baseAttack = 26f,
            baseDefense = 4f,
            attackSpeed = 1.1f,
            image = R.drawable.enemy_guardian_hedgehog_300
        ),
        EnemyCat(
            id = 17,
            name = R.string.enemy_young_raccoon,
            description = R.string.enemy_young_raccoon_desc,
            baseHealth = 110f,
            baseAttack = 20f,
            baseDefense = 2f,
            attackSpeed = 1.5f,
            enemyType = EnemyType.UNIQUE_SUMMONER,
            image = R.drawable.enemy_young_raccoon_300
        ),
        EnemyCat(
            id = 18,
            name = R.string.enemy_mother_raccoon,
            description = R.string.enemy_mother_raccoon_desc,
            baseHealth = 220f,
            baseAttack = 28f,
            baseDefense = 3f,
            attackSpeed = 1.1f,
            image = R.drawable.enemy_mother_raccoon_300
        ),
        EnemyCat(
            id = 19,
            name = R.string.enemy_dark_rogue,
            description = R.string.enemy_dark_rogue_desc,
            baseHealth = 340f,
            baseAttack = 35f,
            baseDefense = 2f,
            attackSpeed = 1f,
            image = R.drawable.enemy_dark_rogue_300
        ),
        EnemyCat(
            id = 20,
            name = R.string.enemy_ant_queen,
            description = R.string.enemy_ant_queen_desc,
            baseHealth = 400f,
            baseAttack = 28f,
            baseDefense = 2f,
            attackSpeed = 1.6f,
            enemyType = EnemyType.SUMMONER,
            image = R.drawable.enemy_queen_ant_300
        ),
        EnemyCat(
            id = 21,
            name = R.string.enemy_hunter_gnome,
            description = R.string.enemy_hunter_gnome_desc,
            baseHealth = 140f,
            baseAttack = 20f,
            baseDefense = 2.5f,
            attackSpeed = 1.1f,
            enemyType = EnemyType.UNIQUE_SUMMONER,
            image = R.drawable.enemy_hunter_gnome_300
        ),
        EnemyCat(
            id = 23,
            name = R.string.enemy_curious_wisp,
            description = R.string.enemy_curious_wisp_desc,
            baseHealth = 320f,
            baseAttack = 26f,
            baseDefense = 1.5f,
            attackSpeed = 0.8f,
            image = R.drawable.enemy_wisp_curious_300
        ),
        EnemyCat(
            id = 24,
            name = R.string.enemy_angry_wisp,
            description = R.string.enemy_angry_wisp_desc,
            baseHealth = 320f,
            baseAttack = 26f,
            baseDefense = 1.5f,
            attackSpeed = 0.8f,
            image = R.drawable.enemy_wisp_angry_300
        ),
        EnemyCat(
            id = 25,
            name = R.string.enemy_sad_wisp,
            description = R.string.enemy_sad_wisp_desc,
            baseHealth = 320f,
            baseAttack = 26f,
            baseDefense = 1.5f,
            attackSpeed = 0.8f,
            image = R.drawable.enemy_wisp_sad_300
        ),
        EnemyCat(
            id = 26,
            name = R.string.enemy_furious_wisp,
            description = R.string.enemy_furious_wisp_desc,
            baseHealth = 320f,
            baseAttack = 26f,
            baseDefense = 1.5f,
            attackSpeed = 0.8f,
            image = R.drawable.enemy_wisp_furious_300
        ),
        EnemyCat(
            id = 27,
            name = R.string.enemy_squirrel_explorer,
            description = R.string.enemy_squirrel_explorer_desc,
            baseHealth = 440f,
            baseAttack = 38f,
            baseDefense = 2.5f,
            attackSpeed = 1.0f,
            image = R.drawable.enemy_squirrel_explorer_300
        ),
        EnemyCat(
            id = 28,
            name = R.string.enemy_squirrel_forager,
            description = R.string.enemy_squirrel_forager_desc,
            baseHealth = 440f,
            baseAttack = 38f,
            baseDefense = 2.5f,
            attackSpeed = 1.0f,
            image = R.drawable.enemy_squirrel_forager_300
        ),
        EnemyCat(
            id = 29,
            name = R.string.enemy_dark_rogue_mage,
            description = R.string.enemy_dark_rogue_mage_desc,
            baseHealth = 340f,
            baseAttack = 42f,
            baseDefense = 2.0f,
            attackSpeed = 1.2f,
            image = R.drawable.enemy_dark_rogue_mage_300
        ),
        EnemyCat(
            id = 30,
            name = R.string.enemy_dark_rogue_warrior,
            description = R.string.enemy_dark_rogue_warrior_desc,
            baseHealth = 420f,
            baseAttack = 34f,
            baseDefense = 3.5f,
            attackSpeed = 1.3f,
            image = R.drawable.enemy_dark_rogue_warrior_300
        ),
        EnemyCat(
            id = 31,
            name = R.string.enemy_leafmasked_raccoon_one,
            description = R.string.enemy_leafmasked_raccoon_one_desc,
            baseHealth = 480f,
            baseAttack = 35f,
            baseDefense = 3.0f,
            attackSpeed = 1.0f,
            image = R.drawable.enemy_leafmasked_raccoon_one_300
        ),
        EnemyCat(
            id = 32,
            name = R.string.enemy_leafmasked_raccoon_two,
            description = R.string.enemy_leafmasked_raccoon_two_desc,
            baseHealth = 480f,
            baseAttack = 35f,
            baseDefense = 3.0f,
            attackSpeed = 1.0f,
            image = R.drawable.enemy_leafmasked_raccoon_two_300
        ),
        EnemyCat(
            id = 33,
            name = R.string.enemy_mushroom_happy,
            description = R.string.enemy_mushroom_happy_desc,
            baseHealth = 180f,
            baseAttack = 36f,
            baseDefense = 4.0f,
            attackSpeed = 1.2f,
            enemyType = EnemyType.MIMIC,
            image = R.drawable.enemy_mushroom_happy_300
        ),
        EnemyCat(
            id = 34,
            name = R.string.enemy_mushroom_angry,
            description = R.string.enemy_mushroom_angry_desc,
            baseHealth = 620f,
            baseAttack = 36f,
            baseDefense = 4.0f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_mushroom_angry_300
        ),
        EnemyCat(
            id = 35,
            name = R.string.enemy_dark_mushroom_happy,
            description = R.string.enemy_dark_mushroom_happy_desc,
            baseHealth = 180f,
            baseAttack = 36f,
            baseDefense = 4.0f,
            attackSpeed = 1.2f,
            enemyType = EnemyType.MIMIC,
            image = R.drawable.enemy_dark_mushroom_happy_300
        ),
        EnemyCat(
            id = 36,
            name = R.string.enemy_dark_mushroom_angry,
            description = R.string.enemy_dark_mushroom_angry_desc,
            baseHealth = 480f,
            baseAttack = 42f,
            baseDefense = 2.0f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_dark_mushroom_angry_300
        ),
        EnemyCat(
            id = 37,
            name = R.string.enemy_dark_vines,
            description = R.string.enemy_dark_vines_desc,
            baseHealth = 860f,
            baseAttack = 42f,
            baseDefense = 5.0f,
            attackSpeed = 1.5f,
            image = R.drawable.enemy_dark_vines_300
        ),
        EnemyCat(
            id = 38,
            name = R.string.enemy_glowbug,
            description = R.string.enemy_glowbug_desc,
            baseHealth = 240f,
            baseAttack = 18f,
            baseDefense = 1.0f,
            attackSpeed = 0.8f,
            image = R.drawable.enemy_glowbug_300
        ),
        EnemyCat(
            id = 39,
            name = R.string.enemy_hollow_beetle,
            description = R.string.enemy_hollow_beetle_desc,
            baseHealth = 660f,
            baseAttack = 52f,
            baseDefense = 6.0f,
            attackSpeed = 1.6f,
            image = R.drawable.enemy_hollow_beetle_300
        ),
        EnemyCat(
            id = 40,
            name = R.string.enemy_darkowl_asleep,
            description = R.string.enemy_darkowl_asleep_desc,
            baseHealth = 240f,
            baseAttack = 60f,
            baseDefense = 5.0f,
            attackSpeed = 2.5f,
            enemyType = EnemyType.MIMIC,
            image = R.drawable.enemy_darkowl_asleep_300
        ),
        EnemyCat(
            id = 41,
            name = R.string.enemy_darkowl,
            description = R.string.enemy_darkowl_desc,
            baseHealth = 1040f,
            baseAttack = 42f,
            baseDefense = 5.0f,
            attackSpeed = 1.2f,
            image = R.drawable.enemy_darkowl_300
        ),
        EnemyCat(
            id = 42,
            name = R.string.enemy_dark_spider,
            description = R.string.enemy_dark_spider_desc,
            baseHealth = 1020f,
            baseAttack = 42f,
            baseDefense = 6.0f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_dark_spider_300
        ),
        EnemyCat(
            id = 43,
            name = R.string.enemy_dark_spider_baby,
            description = R.string.enemy_dark_spider_baby_desc,
            baseHealth = 220f,
            baseAttack = 24f,
            baseDefense = 2.0f,
            attackSpeed = 1.0f,
            enemyType = EnemyType.SUMMONER,
            image = R.drawable.enemy_dark_spider_baby_300
        ),
        EnemyCat(
            id = 44,
            name = R.string.enemy_oldstumpy,
            description = R.string.enemy_oldstumpy_desc,
            baseHealth = 1480f,
            baseAttack = 46f,
            baseDefense = 7.0f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_oldstumpy_300
        ),
        EnemyCat(
            id = 45,
            name = R.string.enemy_phantom_cat,
            description = R.string.enemy_phantom_cat_desc,
            baseHealth = 720f,
            baseAttack = 38f,
            baseDefense = 3.0f,
            attackSpeed = 1.1f,
            enemyType = EnemyType.UNIQUE_SUMMONER,
            image = R.drawable.enemy_phantom_cat_300
        ),
        EnemyCat(
            id = 46,
            name = R.string.enemy_phantom_cat_minion,
            description = R.string.enemy_phantom_cat_minion_desc,
            baseHealth = 480f,
            baseAttack = 42f,
            baseDefense = 3.0f,
            attackSpeed = 1.0f,
            image = R.drawable.enemy_phantom_cat_minion_300
        ),
        EnemyCat(
            id = 47,
            name = R.string.enemy_phantom_squirrel,
            description = R.string.enemy_phantom_squirrel_desc,
            baseHealth = 880f,
            baseAttack = 42f,
            baseDefense = 3.0f,
            attackSpeed = 1.4f,
            image = R.drawable.enemy_phantom_squirrel_300
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
        10L to listOf(19, 10),          // Hose
        11L to listOf(1),               // Ant worker
        12L to listOf(11, 10),          // Ant warrior
        13L to listOf(11, 20),          // Angry snail
        14L to listOf(11, 20),          // Happy Snail
        15L to listOf(1, 13, 21),       // Mosquito
        16L to listOf(11, 12, 10, 26),  // Hedgehog
        17L to listOf(22, 2, 9, 3),     // Young racoon
        18L to listOf(11, 10, 12, 17),  // Mother racoon
        19L to listOf(13, 4, 14, 20),   // Dark rogue
        20L to listOf(23, 24, 19, 20),  // Ant queen
        21L to listOf(11, 26, 20, 25),  // Hunter gnome
        23L to listOf(29, 6, 18),       // Curious Wisp
        24L to listOf(10, 6, 15, 28),   // Angry Wisp
        25L to listOf(30, 2, 6),        // Sad Wisp
        26L to listOf(15, 19, 28, 16),  // Furious Wisp
        27L to listOf(1, 11, 10, 18),   // Squirrel Explorer
        28L to listOf(2, 3, 5, 26),     // Squirrel Forager
        29L to listOf(5, 15, 28, 16),   // Dark Rogue Mage
        30L to listOf(1, 11, 26),       // Dark Rogue Warrior
        31L to listOf(2, 11, 10, 26),   // Leaf-Masked Raccoon One
        32L to listOf(2, 11, 14, 26),   // Leaf-Masked Raccoon Two
        33L to listOf(1, 2, 27, 31),    // Mushroom Happy
        34L to listOf(1, 11, 10, 27),   // Mushroom Angry
        35L to listOf(1, 13, 14, 32),   // Dark Mushroom Happy
        36L to listOf(11, 14, 28, 16),  // Dark Mushroom Angry
        37L to listOf(11, 10, 14, 28),  // Dark Vines
        38L to listOf(1, 18, 28, 33),   // Glowbug
        39L to listOf(2, 5, 11, 10),    // Hollow Beetle
        40L to listOf(34),              // Dark Owl Asleep
        41L to listOf(1, 6, 28, 16),    // Dark Owl
        42L to listOf(11, 14, 28, 16),  // Dark Spider
        43L to listOf(1, 13, 28),       // Dark Baby Spider
        44L to listOf(5, 11, 19, 26),   // Old Stumpy
        45L to listOf(1, 13, 14, 18),   // Phantom Cat
        46L to listOf(29, 30, 1, 15),   // Phantom Cat Minion
        47L to listOf(11, 10, 18, 28),  // Phantom Squirrel
    )

    fun getEnemyAbilities(): List<EnemyAbility> =
        enemyAbilities.flatMap { (catId, abilityIds) ->
            abilityIds.map { abilityId ->
                EnemyAbility(catId, abilityId)
            }
        }
}