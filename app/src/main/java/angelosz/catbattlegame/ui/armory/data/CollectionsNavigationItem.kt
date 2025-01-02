package angelosz.catbattlegame.ui.armory.data

import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.armory.enums.CollectionsView

data class CollectionsNavigationItem(
    val title: String,
    val icon: Int,
    val selectedIcon: Int,
    val view: CollectionsView
)

val armoryNavigationItems by lazy {
    listOf(
        CollectionsNavigationItem(
            title = "Cats",
            icon = R.drawable.cats_icon_96,
            selectedIcon = R.drawable.cats_icon_96,
            view = CollectionsView.CATS
        ),
        CollectionsNavigationItem(
            title = "Teams",
            icon = R.drawable.team_icon_96,
            selectedIcon = R.drawable.team_icon_96,
            view = CollectionsView.TEAMS
        ),
        CollectionsNavigationItem(
            title = "Packages",
            icon = R.drawable.armory_icon_96,
            selectedIcon = R.drawable.armory_icon_96,
            view = CollectionsView.BATTLE_CHESTS
        ),
    )
}

val archivesNavigationItems by lazy {
    listOf(
        CollectionsNavigationItem(
            title = "Cats",
            icon = R.drawable.cats_icon_96,
            selectedIcon = R.drawable.cats_icon_96,
            view = CollectionsView.CATS
        ),
        CollectionsNavigationItem(
            title = "Abilities",
            icon = R.drawable.abilities_icon_96,
            selectedIcon = R.drawable.abilities_icon_96,
            view = CollectionsView.ABILITIES
        ),
        CollectionsNavigationItem(
            title = "Enemies",
            icon = R.drawable.enemies_icon_96,
            selectedIcon = R.drawable.enemies_icon_96,
            view = CollectionsView.ENEMIES
        )
    )
}