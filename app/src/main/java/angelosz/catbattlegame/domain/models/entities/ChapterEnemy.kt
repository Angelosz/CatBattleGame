package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter_enemy")
data class ChapterEnemy (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val chapterId: Long,
    val enemyCatId: Long,
    val order: Int = 1
)