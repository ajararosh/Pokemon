package com.example.marsphotos.test.uitesting

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.HomeScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marsphotos.ui.screens.MarsViewModel

enum class PokemonScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    Entry(title = R.string.entry),
    Info(title = R.string.info),
    Options(title = R.string.options)
}

@Composable
fun PokemonNavigationApp(){
    val navController = rememberNavController()
    val marsViewModel: MarsViewModel =
        viewModel(factory = MarsViewModel.Factory)

    val marsUiState by marsViewModel.marsUiState.collectAsState()
    Scaffold {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Start",
            modifier = Modifier.padding(innerPadding)) {
            composable(route = PokemonScreen.Start.name) {
                StartScreenPokemon(
                    onStartButtonClicked = {
                        navController.navigate(PokemonScreen.Entry.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = PokemonScreen.Entry.name) {
                HomeScreen(
                    marsUiState = marsUiState, // ✅ Pass the collected UI state
                    retryAction = { marsViewModel.getMarsPhotos() },
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        navController.navigate(PokemonScreen.Info.name)
                    }
                )
            }
            composable(route = PokemonScreen.Info.name){
//                PokemonInfoScreen() {
//
//                }

            }
            composable(route = PokemonScreen.Options.name){

            }
        }
    }
}







