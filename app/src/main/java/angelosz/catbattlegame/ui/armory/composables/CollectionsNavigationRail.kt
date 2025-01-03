package angelosz.catbattlegame.ui.armory.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.ui.armory.data.CollectionsNavigationItem
import angelosz.catbattlegame.ui.armory.data.armoryNavigationItems
import angelosz.catbattlegame.ui.armory.enums.CollectionsView

@Composable
fun CollectionsNavigationRail(
    modifier: Modifier = Modifier,
    selectedView: CollectionsView,
    onTabPressed:(CollectionsView) -> Unit,
    hasBackButton: Boolean = true,
    onBackPressed: () -> Unit,
    items: List<CollectionsNavigationItem> = armoryNavigationItems
){
    NavigationRail(
        modifier = modifier,
    ){
        if(hasBackButton){
            NavigationRailItem(
                selected = false,
                onClick = onBackPressed,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            )
        }

        for( item in items ){
            NavigationRailItem(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                selected = item.view == selectedView,
                onClick = { onTabPressed(item.view) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    }
}

