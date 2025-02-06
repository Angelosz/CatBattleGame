package angelosz.catbattlegame.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.utils.GameConstants.ADULT_BATTLECHEST_COST
import angelosz.catbattlegame.utils.GameConstants.KITTEN_BATTLECHEST_COST
import angelosz.catbattlegame.utils.GameConstants.TEEN_BATTLECHEST_COST


@Composable
fun PortraitShopScreen(
    availableGold: Int,
    closeShop: () -> Unit,
    buyBattleChest: (CatRarity) -> Unit
) {
    Button(
        onClick = closeShop,
        modifier = Modifier
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ){}
    Card(
        modifier = Modifier
            .width(304.dp)
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(R.string.package_shop),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            val items = listOf(
                Pair(CatRarity.KITTEN, KITTEN_BATTLECHEST_COST),
                Pair(CatRarity.TEEN, TEEN_BATTLECHEST_COST),
                Pair(CatRarity.ADULT, ADULT_BATTLECHEST_COST),
            )
            repeat(items.size){
                Card(
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            modifier = Modifier.size(64.dp),
                            painter = painterResource(R.drawable.battlechest_256),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                modifier = Modifier.padding(2.dp),
                                text = stringResource(
                                    R.string.type_package,
                                    stringResource(items[it].first.res)
                                ),
                            )
                            Button(
                                enabled = availableGold >= items[it].second,
                                onClick = { buyBattleChest(items[it].first) }
                            ) {
                                Text(
                                    text = "${items[it].second}"
                                )
                                Image(
                                    painter = painterResource(R.drawable.goldcoins_128),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(28.dp)
                                        .padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .padding(8.dp),
                onClick = closeShop
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.close_shop)
                )
            }

        }
    }
}

@Composable
fun LandscapeShopScreen(
    availableGold: Int,
    closeShop: () -> Unit,
    buyBattleChest: (CatRarity) -> Unit
) {
    Button(
        onClick = closeShop,
        modifier = Modifier
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ){}
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(512.dp)
    ){
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(R.string.package_shop),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            val items = listOf(
                Pair(CatRarity.KITTEN, KITTEN_BATTLECHEST_COST),
                Pair(CatRarity.TEEN, TEEN_BATTLECHEST_COST),
                Pair(CatRarity.ADULT, ADULT_BATTLECHEST_COST),
            )
            LazyVerticalGrid(
                modifier = Modifier.height(208.dp),
                columns = GridCells.Fixed(2),
                content = {
                    items(items){ item ->
                        Card(
                            modifier = Modifier.padding(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Image(
                                    modifier = Modifier.size(64.dp),
                                    painter = painterResource(R.drawable.battlechest_256),
                                    contentDescription = null
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.padding(2.dp),
                                        text = stringResource(
                                            R.string.type_package,
                                            stringResource(item.first.res)
                                        ),
                                    )
                                    Button(
                                        enabled = availableGold >= item.second,
                                        onClick = { buyBattleChest(item.first) }
                                    ) {
                                        Text(
                                            text = "${item.second}"
                                        )
                                        Image(
                                            painter = painterResource(R.drawable.goldcoins_128),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(28.dp)
                                                .padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )

            Button(
                modifier = Modifier
                    .padding(8.dp),
                onClick = closeShop
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.close_shop)
                )
            }
        }
    }
}
