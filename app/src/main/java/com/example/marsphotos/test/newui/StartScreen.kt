package com.example.marsphotos.test.newui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.R
import com.example.marsphotos.model.MusicViewModel


@Composable
fun StartScreenPokemon(
    onStartButtonClicked: () -> Unit
) {
    val musicViewModel: MusicViewModel = viewModel()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.pikachu_4k),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(300.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onStartButtonClicked) {
                Text("Start")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreenPokemon(
        onStartButtonClicked = {}
    )
}