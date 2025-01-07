package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R

@Entity(tableName = "campaign")
data class CampaignEntity (
    @PrimaryKey
    val id: Long,
    val name: String = "Campaign",
    val description: String = "Campaign description",
    @DrawableRes val image: Int = R.drawable.house_campaign_icon_256,
    val nextCampaign: Long = 0,
)
