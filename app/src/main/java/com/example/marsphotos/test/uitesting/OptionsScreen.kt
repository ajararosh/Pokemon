package com.example.marsphotos.test.uitesting


import android.graphics.BitmapFactory.Options
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.R

// TODO: NAV HOST - COMPOSABLE - SCREENS

@Composable
fun OptionsScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            // Add the title here, at the top of the Column
            Text(
                text = "Options Screen", // Your title here
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp), // Space for start_ball and text
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Start Ball and Text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = -130.dp)
                ) {
                    Text(
                        text = "Start",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    PressableImage(R.drawable.start_ball,
                        "Game Ball",
                        onClick = {
                            navController.popBackStack(
                                PokemonScreen.Start.name,
                                inclusive = true)
                            navController.navigate(PokemonScreen.Start.name)
                        })
                }
                // Entry Ball and Text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Entry",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    PressableImage(R.drawable.entry_ball,"Entry ball",
                        onClick = {})

                }
                // Game Ball and Text
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(y = -130.dp)
                ) {
                    Text(
                        text = "Game",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    PressableImage(R.drawable.game_ball, "Game Ball",
                        onClick = {})
                }
            }
        }
    }
}

@Composable
fun PressableImage(
    imageRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit // New onClick parameter
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.8f else 1f)

    // Handle the click action
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        modifier = modifier
            .size(100.dp)
            .clickable {
                isPressed = !isPressed // Toggle pressed state
                onClick() // Trigger the onClick action passed as a parameter
            }
            .scale(scale)
    )
}

@Preview(showBackground = true)
@Composable
fun OptionsPreview() {
    OptionsScreen(rememberNavController())
}