package angelosz.catbattlegame.ui.armory.data

import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.armory.enums.CollectionsView

data class CollectionsNavigationItem(
    @StringRes val title: Int,
    val icon: Int,
    val selectedIcon: Int,
    val view: CollectionsView
)

val armoryNavigationItems by lazy {
    listOf(
        CollectionsNavigationItem(
            title = R.string.navigation_item_cats,
            icon = R.drawable.cats_icon_96,
            selectedIcon = R.drawable.cats_icon_96,
            view = CollectionsView.CATS
        ),
        CollectionsNavigationItem(
            title = R.string.navigation_item_teams,
            icon = R.drawable.team_icon_96,
            selectedIcon = R.drawable.team_icon_96,
            view = CollectionsView.TEAMS
        ),
        CollectionsNavigationItem(
            title = R.string.navigation_item_packages,
            icon = R.drawable.armory_icon_96,
            selectedIcon = R.drawable.armory_icon_96,
            view = CollectionsView.BATTLE_CHESTS
        ),
    )
}

val archivesNavigationItems by lazy {
    listOf(
        CollectionsNavigationItem(
            title = R.string.navigation_item_cats,
            icon = R.drawable.cats_icon_96,
            selectedIcon = R.drawable.cats_icon_96,
            view = CollectionsView.CATS
        ),
        CollectionsNavigationItem(
            title = R.string.navigation_item_abilities,
            icon = R.drawable.abilities_icon_96,
            selectedIcon = R.drawable.abilities_icon_96,
            view = CollectionsView.ABILITIES
        ),
        CollectionsNavigationItem(
            title = R.string.navigation_item_enemies,
            icon = R.drawable.enemies_icon_96,
            selectedIcon = R.drawable.enemies_icon_96,
            view = CollectionsView.ENEMIES
        )
    )
}