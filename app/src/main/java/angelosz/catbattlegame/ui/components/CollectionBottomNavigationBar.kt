package angelosz.catbattlegame.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.collectionNavigationItems

@Composable
fun CollectionNavigationBottomBar(
    selectedView: CollectionView,
    onTabPressed: (CollectionView) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier
    ){
        NavigationBarItem(
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
            NavigationBarItem(
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