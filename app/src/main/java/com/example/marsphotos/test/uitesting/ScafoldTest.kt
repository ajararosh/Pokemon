package com.example.marsphotos.test.uitesting

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.PokemonPhotosApplication
import com.example.marsphotos.R
import com.example.marsphotos.data.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.marsphotos.model.Pokemon
import kotlinx.coroutines.flow.asStateFlow

val MyDarkColorScheme = darkColorScheme(
    primary = Color(0xFFEEE9E9),
    secondary = Color(0xFF4F524F),
    background = Color(0xC88C732F),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = Color(0xFF151112),
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0xFF018786),
    onSecondaryContainer = Color.White
)

enum class PokemonScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    Entry(title = R.string.entry),
    Info(title = R.string.info),
    Options(title = R.string.options),
    Game(title = R.string.game)

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    val navController = rememberNavController()
    val pokemonViewModel: PokemonViewModel = viewModel(factory = PokemonViewModel.Factory)

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
        containerColor = Color.Black
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = PokemonScreen.Start.name, modifier = Modifier.padding(innerPadding)) {
            composable(route = PokemonScreen.Start.name) {
                StartScreenPokemon(onStartButtonClicked = { navController.navigate(PokemonScreen.Entry.name) })
            }
            composable(PokemonScreen.Entry.name) {
                HomeScreen(pokemonViewModel = pokemonViewModel, retryAction = { pokemonViewModel.getPokemonPhotos() }, contentPadding = innerPadding, navController = navController)
            }
            composable(PokemonScreen.Game.name) { PokemonGameScreen(onGameButtonClicked = { navController.navigate(PokemonScreen.Start.name) }) }
            composable(PokemonScreen.Info.name) { PokemonInfoScreen(pokemonId = "1") }
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
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { navController.navigate(route) }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Text(text = label)
    }
}


// ================================================================= //
// TODO:==========================SCREENS================================= //
// ================================================================= //


@Composable
fun StartScreenPokemon(onStartButtonClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Start Screen")
        Button(onClick = onStartButtonClicked) {
            Text("Go to Entry")
        }
    }
}
@Composable
fun HomeScreen(
    pokemonViewModel: PokemonViewModel, // Added pokemonViewModel
    retryAction: () -> Unit, // Added retryAction
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(PokemonScreen.Info.name) }) { // Corrected navigation
            Text("Go to Info Screen")
        }
    }
}
@Composable
fun PokemonInfoScreen(pokemonId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Info Screen (Pokemon ID: $pokemonId)")
        }
    }

}
@Composable
fun PokemonGameScreen(onGameButtonClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Game Screen")
        Button(onClick = onGameButtonClicked) {
            Text("Go to Start")
        }
    }
}

// ================================================================= //
// TODO:==========================UI STATE================================= //
// ================================================================= //

sealed interface PokemonUiState {
    data class Success(val photos: List<Pokemon>) : PokemonUiState
    object Error : PokemonUiState
    object Loading : PokemonUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PokemonViewModel(private val PokemonPhotosRepository: PokemonRepository) : ViewModel() {

    private val _pokemonUiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val pokemonUiState: StateFlow<PokemonUiState> = _pokemonUiState.asStateFlow()

    init {
        getPokemonPhotos()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getPokemonPhotos() {
        viewModelScope.launch {
            _pokemonUiState.value = PokemonUiState.Loading
            _pokemonUiState.value = try {
                PokemonUiState.Success(PokemonPhotosRepository.getPokemonList())
            } catch (e: IOException) {
                PokemonUiState.Error
            } catch (e: HttpException) {
                PokemonUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonPhotosApplication)
                val pokemonPhotosRepository = application.container.pokemonPhotosRepository
                PokemonViewModel(pokemonPhotosRepository)
            }
        }
    }
}

// ================================================================= //
// TODO:==========================Preview================================= //
// ================================================================= //

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun PreviewScaffoldExample() {@Composable
fun HomeScreen(
    pokemonViewModel: PokemonViewModel, // Added pokemonViewModel
    retryAction: () -> Unit, // Added retryAction
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(PokemonScreen.Info.name) }) { // Corrected navigation
            Text("Go to Info Screen")
        }
    }
}
    MaterialTheme {
        ScaffoldExample()
    }
}