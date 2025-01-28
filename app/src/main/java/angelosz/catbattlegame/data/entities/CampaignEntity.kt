package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R

@Entity(tableName = "campaign")
data class CampaignEntity (
    @PrimaryKey
    val id: Long,
    @StringRes val name: Int = R.string.campaign_title_1,
    @StringRes val description: Int = R.string.campaign_desc_1,
    @DrawableRes val image: Int = R.drawable.house_campaign_icon_256,
    val nextCampaign: Long = 0,
)
