package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.CollectionNavigationItem
import angelosz.catbattlegame.domain.models.encyclopediaNavigationItems

@Composable
fun CollectionNavigationBottomBar(
    modifier: Modifier = Modifier,
    selectedView: CollectionView,
    onTabPressed: (CollectionView) -> Unit,
    onBackPressed: () -> Unit,
    items: List<CollectionNavigationItem> = encyclopediaNavigationItems
){
    NavigationBar(
        modifier = modifier
    ){
        for( item in items ){
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
        NavigationBarItem(
            selected = false,
            onClick = onBackPressed,
            icon = {
                Image(
                    painter = painterResource(R.drawable.back_button_128),
                    contentDescription = "Back button"
                )
            }
        )
    }
}