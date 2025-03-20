package com.example.marsphotos.test.newui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.R
import com.example.marsphotos.model.MusicViewModel


/***
 * TODO: Mix the audio of pokemon wiht the gif to create a game
 *
 */

@Composable
fun PokemonGameScreen(
    onGameButtonClicked: () -> Unit
){

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize(), //Optional: Change background of the full screen if needed.
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "Coming",
            modifier = Modifier.background(Color.Red),
            fontWeight = FontWeight.Bold,
            fontSize = 80.sp, // Adjust the font size as needed
            textAlign = TextAlign.Center, // Center the text within the Text composable
            color = Color.White, //add white color to the text
            fontFamily = FontFamily.Monospace
        )
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    )
    {
        var onclickbutton = false
        Button(
            onClick =onGameButtonClicked.also {
//                musicViewModel.start()
            },
            colors = ButtonColors(
                Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text("Start")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(), //Optional: Change background of the full screen if needed.
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            text = "Soon...",
            modifier = Modifier.background(Color.Red),
            fontWeight = FontWeight.Bold,
            fontSize = 80.sp, // Adjust the font size as needed
            textAlign = TextAlign.Center, // Center the text within the Text composable
            color = Color.White, //add white color to the text
            fontFamily = FontFamily.Monospace
        )

    }
}
@Composable
@Preview
fun PokemonGamePreview(){
    PokemonGameScreen(
        onGameButtonClicked = {}
    )
}

