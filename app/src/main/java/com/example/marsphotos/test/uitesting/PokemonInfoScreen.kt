package com.example.marsphotos.test.uitesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marsphotos.R

@Composable
fun PokemonInfoScreen(pokemonName: String, height: String, weight: String, baseExp: String, description: String, onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter =  painterResource(R.drawable.pikachu_4k),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.7f
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .size(40.dp)
                .clickable { onBackClick() }
                .offset(y = 40.dp)
            .background(color = Color.White,shape = CircleShape)
            .border(1.dp,Color.Black,shape= CircleShape),
            tint = Color.Black // Ensure proper icon color
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chardizar),
                contentDescription = "Pokemon Image",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = pokemonName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoField(label = "Height", value = height)
                    InfoField(label = "Weight", value = weight)
                    InfoField(label = "Base Exp", value = baseExp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Description: $description",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun InfoField(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White,
            modifier = Modifier.background(color = Color.Black).alpha(0.8f))
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPokemonInfoScreen() {
    PokemonInfoScreen(
        pokemonName = "Pikachu",
        height = "0.4m",
        weight = "6.0kg",
        baseExp = "112",
        description = "A friendly electric-type Pokemon.",
        onBackClick = {}
    )
}