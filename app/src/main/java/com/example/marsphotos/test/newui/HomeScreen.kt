package com.example.marsphotos.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.theme.MarsPhotosTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.marsphotos.R // Replace with your actual package name
//import com.example.marsphotos.test.uitesting.OptionsScreen
import com.example.marsphotos.test.uitesting.PressableImage
//import com.example.marsphotos.test.uitesting.PokemonScreen
import com.example.marsphotos.test.codetest.SearchBarCard
import com.example.marsphotos.test.newui.PokemonScreen
import com.example.marsphotos.test.newui.PokemonUiState
import com.example.marsphotos.test.newui.PokemonViewModel

@Composable
fun HomeScreen(
    pokemonUiState: PokemonUiState,
    retryAction: () -> Unit,
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier // Default Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

//    // -------------------------------------
//    val listState = rememberLazyListState()
//    val isScrollingUp by remember {
//        derivedStateOf {
//            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset <= 0
//        }
//    }
//    // -------------------------------------

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.pikachu_4k),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.5f
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SearchBarCard(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search here",
                onTypeSelected = {},
                modifier = Modifier.background(Color.Black)
            )


            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Handle Different UI States
            when (pokemonUiState) {
                is PokemonUiState.Loading -> {
                    // 🔹 Show Loading Screen
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }

                is PokemonUiState.Success -> {

                    val filteredPokemons = pokemonUiState.photos.filter { pokemon ->
                        pokemon.name.contains(searchQuery, ignoreCase = true)
                    }

                    PhotosGridScreen(
                        pokemons = filteredPokemons,
                        contentPadding = contentPadding,
                        modifier = Modifier.fillMaxSize(),
                        onPokemonClick = { pokemon ->
                            navController.navigate(PokemonScreen.Info.name)
                        }
                    )
                }

                is PokemonUiState.Error -> {
                    // 🔹 Show Error Screen
                    ErrorScreen(
                        retryAction,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}




/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun PhotosGridScreen(
    pokemons: List<Pokemon>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onPokemonClick: (Pokemon) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = pokemons, key = {
            pokemon -> pokemon.name }) { pokemon ->
            PokemonCard(
                pokemon, modifier = Modifier.padding(4.dp),
                onClick = { onPokemonClick(pokemon) }
            )

        }

    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val pokemonId = pokemon.url.split("/").dropLast(1).last()
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
    //    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/$pokemonId.gif"


    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.weight(0.8f)// to show the text don't use fillMaxSize()
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(
                text = pokemon.name.capitalize(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    MarsPhotosTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    MarsPhotosTheme {
        ErrorScreen({}) // Pass an empty retry action
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    MarsPhotosTheme {
        val mockData = List(10) { Pokemon("$it", "") }
        PhotosGridScreen(
            mockData,
            onPokemonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController() // Create a navController for preview
    HomeScreen(
        pokemonUiState = PokemonUiState.Success(listOf(Pokemon("bulbasaur", "url"))),
        retryAction = {},
        navController = rememberNavController(),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
    )
}
