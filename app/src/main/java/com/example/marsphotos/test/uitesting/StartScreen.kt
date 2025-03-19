package com.example.marsphotos.test.uitesting


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.R
import com.example.marsphotos.model.MusicViewModel


@Composable
fun StartScreenPokemon(
    onStartButtonClicked: () -> Unit,
    navHost: NavHostController,
    modifier: Modifier = Modifier
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
            Button(onClick = {
//                musicViewModel.start()
                    navHost.navigate(PokemonScreen.Entry.name)
                 }) {
                Text("Start")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreenPokemon(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxSize(),
        onStartButtonClicked = {},
        navHost = rememberNavController()
    )
}