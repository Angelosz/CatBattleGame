package angelosz.catbattlegame.ui.archives.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CatRarity

private val filterItems = listOf(
    CatRarity.KITTEN,
    CatRarity.TEEN,
    CatRarity.ADULT,
    CatRarity.ELDER,
)

@Composable
fun CatRarityFilter(
    modifier: Modifier = Modifier,
    onRaritySelected: (CatRarity) -> Unit = {},
    onRemoveSelection: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRarity by remember { mutableIntStateOf(filterItems.size) }

    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { expanded = !expanded }
    ){
        Text(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
            text =
            if(selectedRarity < filterItems.size)
                stringResource(filterItems[selectedRarity].res)
            else stringResource(R.string.filter),
            style = MaterialTheme.typography.labelLarge
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            shape = RectangleShape,
            containerColor = Color.White
        ){
            for(item in filterItems){
                DropdownMenuItem(
                    text = { Text(stringResource(item.res)) },
                    onClick = {
                        selectedRarity = filterItems.indexOf(item)
                        onRaritySelected(item)
                        expanded = false
                    }
                )
            }
            DropdownMenuItem(
                text = { Text(stringResource(R.string.reset)) },
                onClick = {
                    selectedRarity = filterItems.size
                    onRemoveSelection()
                    expanded = false
                }
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilterPreview(){
    CatRarityFilter()
}