package com.example.marsphotos.test.uitesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.R


@Composable
fun PokemonGameScreen(){
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
    PokemonGameScreen()
}

