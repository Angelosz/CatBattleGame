package angelosz.catbattlegame.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import angelosz.catbattlegame.domain.enums.CollectionView

data class CollectionNavigationItem(
    val collectionView: CollectionView,
    val icon: ImageVector,
    val text: String
)

val encyclopediaNavigationItems by lazy {
    listOf(
        CollectionNavigationItem(
            CollectionView.CATS,
            Icons.Filled.Face,
            text = "Cats"
        ),
        CollectionNavigationItem(
            CollectionView.ABILITIES,
            Icons.Filled.Star,
            text = "Abilities"
        )
    )
}

val collectionNavigationItems by lazy {
    listOf(
        CollectionNavigationItem(
            CollectionView.CATS,
            Icons.Filled.Face,
            text = "Cats"
        )
    )
}