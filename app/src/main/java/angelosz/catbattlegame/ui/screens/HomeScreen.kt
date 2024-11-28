package angelosz.catbattlegame.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R

@Composable
fun HomeScreen(windowSize: WindowWidthSizeClass){
    Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize(),
        ){
            Image(
                painter = painterResource(R.drawable.homescreen_portrait),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.fillMaxSize().weight(0.6f))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .width(192.dp)
                        .height(48.dp)
                        .weight(0.1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Play", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .width(192.dp)
                        .height(48.dp)
                        .weight(0.1f),
                    shape = MaterialTheme.shapes.medium

                ) {
                    Text(text = "My Collection", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .width(192.dp)
                        .height(48.dp)
                        .weight(0.1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Encyclopedia", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(modifier = Modifier.fillMaxSize().weight(0.1f))
            }
        }
    }
}