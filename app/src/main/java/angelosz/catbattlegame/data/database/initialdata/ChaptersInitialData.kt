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
            description = "\"Seems like a scout!\"",
            image = R.drawable.enemy_chapter_sparrow,
            unlocksChapter = 5,
        ),
        CampaignChapter(
            id = 5,
            campaignId = 2,
            order = 2,
            name = "Guards!",
            description = "\"What are they defending?\"",
            image = R.drawable.enemy_chapter_birdguards,
            unlocksChapter = 6,
        )
        /*  End Second Campaign */
    )

    val chapterRewards = listOf(
        /* First Campaign */
        ChapterReward(
            chapterId = 1,
            rewardType = RewardType.GOLD,
            amount = 32
        ),
        ChapterReward(
            chapterId = 1,
            rewardType = RewardType.NEW_KITTEN,
        ),
        ChapterReward(
            chapterId = 2,
            rewardType = RewardType.GOLD,
            amount = 48
        ),
        ChapterReward(
            chapterId = 2,
            rewardType = RewardType.NEW_KITTEN,
        ),
        ChapterReward(
            chapterId = 3,
            rewardType = RewardType.GOLD,
            amount = 60
        ),
        ChapterReward(
            chapterId = 3,
            rewardType = RewardType.NEW_KITTEN,
        ),
        ChapterReward(
            chapterId = 3,
            rewardType = RewardType.KITTEN_BOX,
        ),
        /*  End First Campaign */

        /* Second Campaign */
        ChapterReward(
            chapterId = 4,
            rewardType = RewardType.GOLD,
            amount = 80
        ),
        ChapterReward(
            chapterId = 4,
            rewardType = RewardType.KITTEN_BOX,
        ),
        ChapterReward(
            chapterId = 5,
            rewardType = RewardType.GOLD,
            amount = 120
        ),
        ChapterReward(
            chapterId = 5,
            rewardType = RewardType.KITTEN_BOX,
        ),
        /*  End Second Campaign */
    )

    val chapterEnemies = listOf(
        /* First Campaign */
        ChapterEnemy(chapterId = 1, enemyCatId = 1, order = 1),
        ChapterEnemy(chapterId = 2, enemyCatId = 2, order = 1),
        ChapterEnemy(chapterId = 2, enemyCatId = 2, order = 2),
        ChapterEnemy(chapterId = 3, enemyCatId = 3, order = 1),
        /*  End First Campaign */

        /* Second Campaign */
        ChapterEnemy(chapterId = 4, enemyCatId = 4, order = 1),
        ChapterEnemy(chapterId = 5, enemyCatId = 5, order = 1),
        ChapterEnemy(chapterId = 5, enemyCatId = 6, order = 2),
        /*  End Second Campaign */
    )
}