
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotos.model.Pokemon
import com.example.marsphotos.ui.theme.MarsPhotosTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R // Replace with your actual package name

@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var searchQuery by remember { mutableStateOf("") }

    Box( // Use Box to stack background and content
        modifier = modifier.fillMaxSize()
    ) {
        // Background Image (Fill entire screen)
        Image(
            painter = painterResource(id = R.drawable.pikachu_4k), // Replace with your image
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f)) // Adjust alpha value
        )

        // Content Column (On top of background and overlay)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding) // Use contentPadding from parameters
        ) {
            // ✅ Search Bar (Fixed at the Top)
            SearchBar(
                searchText = searchQuery,
                onSearchTextChanged = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(8.dp)) // Adds spacing

            when (marsUiState) {
                is MarsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                is MarsUiState.Success -> {
                    // ✅ Filter Pokémon list based on search query
                    val filteredPokemons = marsUiState.photos.filter { pokemon ->
                        pokemon.name.contains(searchQuery, ignoreCase = true)
                    }

                    // ✅ Scrollable Grid (Search Bar stays at the top)
                    PhotosGridScreen(
                        pokemons = filteredPokemons,
                        contentPadding = contentPadding,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is MarsUiState.Error -> ErrorScreen(retryAction, modifier = Modifier.fillMaxSize())
            }
        }
    }
}



@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { onSearchTextChanged(it) },
        label = { Text("Search Pokémon") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp, top = 4.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { /* Handle search action if needed */ }
        )
    )
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
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = pokemons, key = { pokemon -> pokemon.name }) { pokemon ->
            PokemonCard(pokemon, modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
fun PokemonCard(pokemon: Pokemon, modifier: Modifier = Modifier) {
    val pokemonId = pokemon.url.split("/").dropLast(1).last()
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Pokémon Details") },
            text = {
                Column {
                    Text("Name: ${pokemon.name.capitalize()}")
                    // You can add more details such as type, abilities, etc.
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { showDialog = true },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)// no elevation
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        Text(
            text = pokemon.name.capitalize(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
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
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    MarsPhotosTheme {
        val mockData = List(10) { Pokemon("$it", "") }
        PhotosGridScreen(mockData)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MarsPhotosTheme {
        HomeScreen(marsUiState = MarsUiState.Success(listOf(Pokemon("bulbasaur", "url"))), retryAction = {})
    }
}