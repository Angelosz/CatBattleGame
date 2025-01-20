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
            name = "The dangers of playing",
            description = "\"Round and round it goes\"",
            image = R.drawable.enemy_chapter_wool_ball_256,
            unlocksChapter = 2,
        ),
        CampaignChapter(
            id = 2,
            campaignId = 1,
            order = 2,
            experience = 200,
            name = "Mice!",
            description = "\"Quick! Catch them!\"",
            image = R.drawable.enemy_chapter_mouse_thief_256,
            unlocksChapter = 3,
        ),
        CampaignChapter(
            id = 3,
            campaignId = 1,
            order = 3,
            experience = 400,
            name = "The enemy",
            description = "\"She wants to play! RUN!\"",
            image = R.drawable.enemy_chapter_puppy_256,
            unlocksChapter = 4,
            isLastCampaignChapter = true
        ),
        /*  End First Campaign */

        /* Second Campaign */
        CampaignChapter(
            id = 4,
            campaignId = 2,
            order = 1,
            name = "Sparrowing",
            description = "\"A little sparrow exploring the woods\"",
            image = R.drawable.enemy_chapter_sparrow,
            unlocksChapter = 5,
        ),
        CampaignChapter(
            id = 5,
            campaignId = 2,
            order = 2,
            name = "Garden Snails",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_snails,
            unlocksChapter = 6,
        ),
        CampaignChapter(
            id = 6,
            campaignId = 2,
            order = 3,
            name = "Garden Gnome Squad",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_angry_gnome,
            unlocksChapter = 7,
        ),
        CampaignChapter(
            id = 7,
            campaignId = 2,
            order = 4,
            name = "Garden Tools",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_garden_tools,
            unlocksChapter = 8,
        ),
        CampaignChapter(
            id = 8,
            campaignId = 2,
            order = 5,
            name = "Worker Ants",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_worker_ant,
            unlocksChapter = 9,
        ),
        CampaignChapter(
            id = 9,
            campaignId = 2,
            order = 6,
            name = "Hunter Gnome",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_hunter_gnome,
            unlocksChapter = 10,
        ),
        CampaignChapter(
            id = 10,
            campaignId = 2,
            order = 7,
            name = "Mosquitos!",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_mosquito,
            unlocksChapter = 11,
        ),
        CampaignChapter(
            id = 11,
            campaignId = 2,
            order = 8,
            name = "Young Racoon",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_racoon,
            unlocksChapter = 12,
        ),
        CampaignChapter(
            id = 12,
            campaignId = 2,
            order = 9,
            name = "Hedgehog",
            description = "\"Chomp Chomp\"",
            image = R.drawable.enemy_chapter_hedgehog,
            unlocksChapter = 13,
        ),
        CampaignChapter(
            id = 13,
            campaignId = 2,
            order = 10,
            name = "Ant Colony",
            description = "\"Ants\"",
            image = R.drawable.enemy_chapter_ant_queen,
            unlocksChapter = 14,
        ),
        CampaignChapter(
            id = 14,
            campaignId = 2,
            order = 11,
            name = "Guards!",
            description = "\"What are they defending?\"",
            image = R.drawable.enemy_chapter_birdguards,
            unlocksChapter = 15,
        ),
        CampaignChapter(
            id = 15,
            campaignId = 2,
            order = 12,
            name = "The Dark Rogue!",
            description = "\"Where are they going?!\"",
            image = R.drawable.enemy_chapter_dark_rogue,
            unlocksChapter = 16,
            isLastCampaignChapter = true
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
                type = RewardType.NEW_KITTEN,
            ),
        ),
        2L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 34
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
                type = RewardType.KITTEN_BOX,
            ),
        ),
        5L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 52
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
                type = RewardType.KITTEN_BOX,
            ),
        ),
        7L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
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
                type = RewardType.KITTEN_BOX,
            ),
        ),
        9L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        10L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        11L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        12L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        13L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        14L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        15L to listOf(
            Reward(
                type = RewardType.GOLD,
                amount = 60
            ),
            Reward(
                type = RewardType.KITTEN_BOX,
            ),
        ),
        /*  End Second Campaign */
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
        8L to listOf(11L),
        9L to listOf(7L, 8L),
        10L to listOf(15L, 15L),
        11L to listOf(17L),
        12L to listOf(16L),
        13L to listOf(11L),
        14L to listOf(5L, 6L),
        15L to listOf(19L),
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