package angelosz.catbattlegame.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.collectionNavigationItems

@Composable
fun CollectionNavigationRail(
    selectedView: CollectionView,
    onTabPressed: (CollectionView) -> Unit,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
){
    NavigationRail(modifier = modifier){
        NavigationRailItem(
            selected = false,
            onClick = onBackPressed,
            icon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        )
        for( item in collectionNavigationItems ){
            NavigationRailItem(
                selected = item.collectionView == selectedView,
                onClick = { onTabPressed(item.collectionView) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.text
                    )
                }
            )
        }
    }
}