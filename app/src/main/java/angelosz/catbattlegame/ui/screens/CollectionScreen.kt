package angelosz.catbattlegame.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.ui.viewmodels.CatCollectionViewModel

@Composable
fun CollectionScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
){
    BackHandler { onBackPressed() }

    val viewModel: CatCollectionViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()

    val cats = uiState.value.ownedCats

    LazyColumn {
        items(cats) { cat ->
            Card(modifier = Modifier.padding(16.dp)){
                Column(
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "Cat: ${cat.cat.name}"
                    )
                    Text(
                        text = "Level: ${cat.level}"
                    )
                    Text(
                        text = "Experience: ${cat.experience}"
                    )
                }
            }
        }
    }
}