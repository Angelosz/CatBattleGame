package angelosz.catbattlegame.ui.armory.data

import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.armory.ArmoryView

data class ArmoryNavigationItem(
    val title: String,
    val icon: Int,
    val selectedIcon: Int,
    val view: ArmoryView
)

val armoryNavigationItems by lazy {
    listOf(
        ArmoryNavigationItem(
            title = "Cats",
            icon = R.drawable.encyclopedia_icon_96,
            selectedIcon = R.drawable.encyclopedia_icon_96,
            view = ArmoryView.CATS
        ),
        ArmoryNavigationItem(
            title = "Teams",
            icon = R.drawable.team_icon_96,
            selectedIcon = R.drawable.team_icon_96,
            view = ArmoryView.TEAMS
        ),
        ArmoryNavigationItem(
            title = "Packages",
            icon = R.drawable.battlechest_icon_96,
            selectedIcon = R.drawable.battlechest_icon_96,
            view = ArmoryView.BATTLE_CHESTS
        ),
    )
}