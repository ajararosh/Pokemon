package com.example.marsphotos.test.uitesting

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.marsphotos.ui.screens.MarsViewModel


val MyDarkColorScheme = darkColorScheme(
    primary = Color(0xFFEEE9E9), // Example primary color for dark theme
    secondary = Color(0xFF4F524F), // Example secondary color for dark theme
    background = Color(0xC88C732F), // Dark background color
    surface = Color(0xFF1E1E1E), // Dark surface color
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = Color(0xFF151112), // Dark primary container
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0xFF018786), // Dark secondary container
    onSecondaryContainer = Color.White
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    val navController = rememberNavController()
    var presses by remember { mutableIntStateOf(0) }
    val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory) // Get ViewModel

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        bottomBar = {
            BottomAppBar(
                containerColor = MyDarkColorScheme.primaryContainer,
                contentColor = MyDarkColorScheme.primary,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarItem(icon = Icons.AutoMirrored.Filled.List, label = PokemonScreen.Start.name, navController = navController, route = PokemonScreen.Start.name)
                    BottomBarItem(icon = Icons.Filled.Info, label = PokemonScreen.Entry.name, navController = navController, route = PokemonScreen.Entry.name)
                    BottomBarItem(icon = Icons.Filled.Games, label = PokemonScreen.Game.name, navController = navController, route = PokemonScreen.Game.name)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = Color.Black
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = PokemonScreen.Start.name, modifier = Modifier.padding(innerPadding)) {
            composable(route = PokemonScreen.Start.name) {
                StartScreenPokemon(onStartButtonClicked = {
                    navController.navigate(PokemonScreen.Entry.name)
                }, navHost = navController)
            }
            composable(PokemonScreen.Entry.name) {
                HomeScreen(
                    marsUiState = marsViewModel ,// Pass ViewModel
                    retryAction = { marsViewModel.getMarsPhotos() },
                    contentPadding = innerPadding,
                    navController = navController
                )
            }
            composable(PokemonScreen.Game.name) { PokemonGameScreen() }
            composable(PokemonScreen.Info.name) {
                PokemonInfoScreen(
                    pokemonId = "1"
                ) {}
            }
        }
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, label: String, navController: NavHostController, route: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.9f else 1f, tween(100))

    Column(
        modifier = Modifier
            .scale(scale.value)
            .clickable(interactionSource = interactionSource, indication = null, onClick = {
                navController.navigate(route)
            }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Text(text = label)
    }
}


@Composable
fun BottomBarItem(icon: ImageVector, label: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.9f else 1f, tween(100))

    Column(
        modifier = Modifier
            .scale(scale.value)
            .clickable(interactionSource = interactionSource, indication = null, onClick = {})
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScaffoldExample() {
    MaterialTheme {
        ScaffoldExample()
    }
}