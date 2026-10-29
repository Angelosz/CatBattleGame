package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.enums.RewardType

class ChaptersInitialData {
    val campaignChapters = listOf(
        /* First Campaign */
        CampaignChapter(
            id = 1,
            campaignId = 1,
            order = 1,
            name = R.string.chapter_title_1,
            description = R.string.chapter_desc_1,
            image = R.drawable.enemy_chapter_wool_ball_256,
            unlocksChapter = 2,
            recommendedLevel = 1
        ),
        CampaignChapter(
            id = 2,
            campaignId = 1,
            order = 2,
            experience = 125,
            name = R.string.chapter_title_2,
            description = R.string.chapter_desc_2,
            image = R.drawable.enemy_chapter_mouse_thief_256,
            unlocksChapter = 3,
            recommendedLevel = 1
        ),
        CampaignChapter(
            id = 3,
            campaignId = 1,
            order = 3,
            experience = 150,
            name = R.string.chapter_title_3,
            description = R.string.chapter_desc_3,
            image = R.drawable.enemy_chapter_puppy_256,
            unlocksChapter = 4,
            isLastCampaignChapter = true,
            recommendedLevel = 1
        ),
        /*  End First Campaign */

        /* Second Campaign */
        CampaignChapter(
            id = 4,
            campaignId = 2,
            order = 1,
            experience = 150,
            name = R.string.chapter_title_4,
            description = R.string.chapter_desc_4,
            image = R.drawable.enemy_chapter_sparrow,
            unlocksChapter = 5,
            recommendedLevel = 1
        ),
        CampaignChapter(
            id = 5,
            campaignId = 2,
            order = 2,
            experience = 150,
            name = R.string.chapter_title_5,
            description = R.string.chapter_desc_5,
            image = R.drawable.enemy_chapter_snails,
            unlocksChapter = 6,
            recommendedLevel = 1
        ),
        CampaignChapter(
            id = 6,
            campaignId = 2,
            order = 3,
            experience = 175,
            name = R.string.chapter_title_6,
            description = R.string.chapter_desc_6,
            image = R.drawable.enemy_chapter_angry_gnome,
            unlocksChapter = 7,
            recommendedLevel = 1
        ),
        CampaignChapter(
            id = 7,
            campaignId = 2,
            order = 4,
            experience = 175,
            name = R.string.chapter_title_7,
            description = R.string.chapter_desc_7,
            image = R.drawable.enemy_chapter_garden_tools,
            unlocksChapter = 8,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 8,
            campaignId = 2,
            order = 5,
            experience = 175,
            name = R.string.chapter_title_8,
            description = R.string.chapter_desc_8,
            image = R.drawable.enemy_chapter_worker_ant,
            unlocksChapter = 9,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 9,
            campaignId = 2,
            order = 6,
            experience = 200,
            name = R.string.chapter_title_9,
            description = R.string.chapter_desc_9,
            image = R.drawable.enemy_chapter_hunter_gnome,
            unlocksChapter = 10,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 10,
            campaignId = 2,
            order = 7,
            experience = 200,
            name = R.string.chapter_title_10,
            description = R.string.chapter_desc_10,
            image = R.drawable.enemy_chapter_mosquito,
            unlocksChapter = 11,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 11,
            campaignId = 2,
            order = 8,
            experience = 200,
            name = R.string.chapter_title_11,
            description = R.string.chapter_desc_11,
            image = R.drawable.enemy_chapter_raccoon,
            unlocksChapter = 12,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 12,
            campaignId = 2,
            order = 9,
            experience = 225,
            name = R.string.chapter_title_12,
            description = R.string.chapter_desc_12,
            image = R.drawable.enemy_chapter_hedgehog,
            unlocksChapter = 13,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 13,
            campaignId = 2,
            order = 10,
            experience = 225,
            name = R.string.chapter_title_13,
            description = R.string.chapter_desc_13,
            image = R.drawable.enemy_chapter_ant_queen,
            unlocksChapter = 14,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 14,
            campaignId = 2,
            order = 11,
            experience = 225,
            name = R.string.chapter_title_14,
            description = R.string.chapter_desc_14,
            image = R.drawable.enemy_chapter_birdguards,
            unlocksChapter = 15,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 15,
            campaignId = 2,
            order = 12,
            experience = 250,
            name = R.string.chapter_title_15,
            description = R.string.chapter_desc_15,
            image = R.drawable.enemy_chapter_dark_rogue,
            unlocksChapter = 16,
            isLastCampaignChapter = true,
            recommendedLevel = 2
        ),
        /*  End Second Campaign */

        /* Third Campaign */
        CampaignChapter(
            id = 16,
            campaignId = 3,
            order = 1,
            experience = 250,
            name = R.string.chapter_title_16,
            description = R.string.chapter_desc_16,
            image = R.drawable.enemy_chapter_16_wisps,
            unlocksChapter = 17,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 17,
            campaignId = 3,
            order = 2,
            experience = 275,
            name = R.string.chapter_title_17,
            description = R.string.chapter_desc_17,
            image = R.drawable.enemy_chapter_17_adventurer_squirrels,
            unlocksChapter = 18,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 18,
            campaignId = 3,
            order = 3,
            experience = 275,
            name = R.string.chapter_title_18,
            description = R.string.chapter_desc_18,
            image = R.drawable.enemy_chapter_18_dark_rogue_friends,
            unlocksChapter = 19,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 19,
            campaignId = 3,
            order = 4,
            experience = 275,
            name = R.string.chapter_title_19,
            description = R.string.chapter_desc_19,
            image = R.drawable.enemy_chapter_19_angry_wisps,
            unlocksChapter = 20,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 20,
            campaignId = 3,
            order = 5,
            experience = 275,
            name = R.string.chapter_title_20,
            description = R.string.chapter_desc_20,
            image = R.drawable.enemy_chapter_20_leafmasked_raccoons,
            unlocksChapter = 21,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 21,
            campaignId = 3,
            order = 6,
            experience = 300,
            name = R.string.chapter_title_21,
            description = R.string.chapter_desc_21,
            image = R.drawable.enemy_chapter_21_possessed_mushrooms,
            unlocksChapter = 22,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 22,
            campaignId = 3,
            order = 7,
            experience = 300,
            name = R.string.chapter_title_22,
            description = R.string.chapter_desc_22,
            image = R.drawable.enemy_chapter_22_dark_leafvine,
            unlocksChapter = 23,
            recommendedLevel = 4
        ),
        CampaignChapter(
            id = 23,
            campaignId = 3,
            order = 8,
            experience = 300,
            name = R.string.chapter_title_23,
            description = R.string.chapter_desc_23,
            image = R.drawable.enemy_chapter_22_hollow_beetle,
            unlocksChapter = 24,
            recommendedLevel = 4
        ),
        CampaignChapter(
            id = 24,
            campaignId = 3,
            order = 9,
            experience = 325,
            name = R.string.chapter_title_24,
            description = R.string.chapter_desc_24,
            image = R.drawable.enemy_chapter_23_dark_sleepy_owl,
            unlocksChapter = 25,
            recommendedLevel = 4
        ),
        CampaignChapter(
            id = 25,
            campaignId = 3,
            order = 10,
            experience = 325,
            name = R.string.chapter_title_25,
            description = R.string.chapter_desc_25,
            image = R.drawable.enemy_chapter_24_dark_giant_spider,
            unlocksChapter = 26,
            recommendedLevel = 5
        ),
        CampaignChapter(
            id = 26,
            campaignId = 3,
            order = 11,
            experience = 325,
            name = R.string.chapter_title_26,
            description = R.string.chapter_desc_26,
            image = R.drawable.enemy_chapter_25_old_stumpy,
            unlocksChapter = 27,
            recommendedLevel = 5
        ),
        CampaignChapter(
            id = 27,
            campaignId = 3,
            order = 12,
            experience = 350,
            name = R.string.chapter_title_27,
            description = R.string.chapter_desc_27,
            image = R.drawable.enemy_chapter_26_phantoms,
            unlocksChapter = 0,
            isLastCampaignChapter = true,
            recommendedLevel = 5
        ),
        /*  End Third Campaign */

        /* Halloween Campaign (ID 1001) */
        CampaignChapter(
            id = 28,
            campaignId = 1001,
            order = 1,
            experience = 200,
            name = R.string.chapter_title_28,
            description = R.string.chapter_desc_28,
            image = R.drawable.enemy_chapter_halloween_1_scarecrow,
            unlocksChapter = 29,
            recommendedLevel = 2
        ),
        CampaignChapter(
            id = 29,
            campaignId = 1001,
            order = 2,
            experience = 300,
            name = R.string.chapter_title_29,
            description = R.string.chapter_desc_29,
            image = R.drawable.enemy_chapter_halloween_2_necromancer,
            unlocksChapter = 30,
            recommendedLevel = 3
        ),
        CampaignChapter(
            id = 30,
            campaignId = 1001,
            order = 3,
            experience = 350,
            name = R.string.chapter_title_30,
            description = R.string.chapter_desc_30,
            image = R.drawable.enemy_chapter_halloween_3_witch,
            unlocksChapter = 31,
            recommendedLevel = 4
        ),
        CampaignChapter(
            id = 31,
            campaignId = 1001,
            order = 4,
            experience = 400,
            name = R.string.chapter_title_31,
            description = R.string.chapter_desc_31,
            image = R.drawable.enemy_chapter_halloween_4_headlesshorseman,
            unlocksChapter = 0,
            isLastCampaignChapter = true,
            recommendedLevel = 5
        )
        /*  End Second Campaign */
    )

    data class Reward(
        val type: RewardType,
        val amount: Int = 1
    )

    val chapterRewards: Map<Long, List<Reward>> = mapOf(
        /* First Campaign */
        1L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 28
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 10
            ),
            Reward(
                type = RewardType.NEW_KITTEN,
            ),
        ),
        2L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 34
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 10
            ),
            Reward(
                type = RewardType.NEW_KITTEN,
            )
        ),
        3L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 38
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 20
            ),
            Reward(
                type = RewardType.NEW_KITTEN,
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        /*  End First Campaign */

        /* Second Campaign */
        4L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 44
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        5L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 52
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        6L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        7L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        8L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        9L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        10L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 72
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        11L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 72
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        12L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 72
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.TEEN_BOX,
            ),
        ),
        13L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 90
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        14L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 90
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 15
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        15L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 90
            ),
            Reward(
                type = RewardType.CRYSTAL,
                amount = 30
            ),
            Reward(
                type = RewardType.TEEN_BOX,
            ),
        ),
        /*  End Second Campaign */

        /* Third Campaign */
        16L to listOf(Reward(RewardType.GOLD, 100), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        17L to listOf(Reward(RewardType.GOLD, 100), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        18L to listOf(Reward(RewardType.GOLD, 110), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        19L to listOf(Reward(RewardType.GOLD, 110), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        20L to listOf(Reward(RewardType.GOLD, 120), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        21L to listOf(Reward(RewardType.GOLD, 120), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.TEEN_BOX)),
        22L to listOf(Reward(RewardType.GOLD, 130), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        23L to listOf(Reward(RewardType.GOLD, 130), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        24L to listOf(Reward(RewardType.GOLD, 140), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.TEEN_BOX)),
        25L to listOf(Reward(RewardType.GOLD, 140), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        26L to listOf(Reward(RewardType.GOLD, 150), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.TEEN_BOX)),
        27L to listOf(Reward(RewardType.GOLD, 180), Reward(RewardType.CRYSTAL, 40), Reward(RewardType.ADULT_BOX)),
        /* End Third Campaign */

        /* Halloween Campaign (ID 1001) */
        28L to listOf(Reward(RewardType.GOLD, 100), Reward(RewardType.CRYSTAL, 20), Reward(RewardType.KITTEN_BOX)),
        29L to listOf(Reward(RewardType.GOLD, 150), Reward(RewardType.CRYSTAL, 25), Reward(RewardType.KITTEN_BOX)),
        30L to listOf(Reward(RewardType.GOLD, 200), Reward(RewardType.CRYSTAL, 35), Reward(RewardType.TEEN_BOX)),
        31L to listOf(Reward(RewardType.GOLD, 300), Reward(RewardType.CRYSTAL, 50), Reward(RewardType.TEEN_BOX)),
        /* End Halloween Campaign */
    )

    fun getChapterRewards(): List<ChapterReward> =
        chapterRewards.flatMap { (id, rewards) ->
            rewards.map{ reward ->
                ChapterReward(
                    chapterId = id,
                    rewardType = reward.type,
                    amount = reward.amount
                )
            }
        }

    val enemies: Map<Long, List<Long>> = mapOf(
        1L to listOf(1L),
        2L to listOf(2L, 2L),
        3L to listOf(3L),
        4L to listOf(4L),
        5L to listOf(13L, 14L),
        6L to listOf(7L, 7L, 7L, 7L),
        7L to listOf(9L, 10L),
        8L to listOf(12L, 12L, 11L, 11L),
        9L to listOf(21L, 8L),
        10L to listOf(15L, 15L),
        11L to listOf(17L),
        12L to listOf(16L),
        13L to listOf(20L, 11L),
        14L to listOf(5L, 6L),
        15L to listOf(19L),
        /* Third Campaign */
        16L to listOf(23L, 25L),
        17L to listOf(27L, 28L),
        18L to listOf(19L, 29L, 30L),
        19L to listOf(26L, 24L, 23L, 25L),
        20L to listOf(31L, 32L),
        21L to listOf(33L, 35L),
        22L to listOf(37L, 38L, 38L, 38L),
        23L to listOf(39L, 39L),
        24L to listOf(40L),
        25L to listOf(42L, 43L, 43L, 43L),
        26L to listOf(44L),
        27L to listOf(45L, 46L, 47L),
        /* Halloween Campaign (ID 1001) */
        28L to listOf(48L, 49L, 49L),
        29L to listOf(50L, 51L),
        30L to listOf(52L, 53L, 54L, 55L),
        31L to listOf(56L),
    )

    fun getChapterEnemies(): List<ChapterEnemy> = enemies.flatMap { (id, enemies) ->
        enemies.map { enemy ->
            ChapterEnemy(
                chapterId = id,
                enemyCatId = enemy
            )
        }
    }
}