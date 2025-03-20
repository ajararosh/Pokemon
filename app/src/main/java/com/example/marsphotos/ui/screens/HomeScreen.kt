//package com.example.marsphotos.ui.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import com.example.marsphotos.model.Pokemon
//import com.example.marsphotos.ui.theme.MarsPhotosTheme
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.*
//
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.marsphotos.R // Replace with your actual package name
////import com.example.marsphotos.test.uitesting.OptionsScreen
//import com.example.marsphotos.test.uitesting.PressableImage
//import com.example.marsphotos.test.uitesting.PokemonScreen
//import com.example.marsphotos.test.codetest.SearchBarCard
//
//
//
//@Composable
//fun HomeScreen(
//    marsUiState: MarsUiState.Success,
//    retryAction: () -> Unit,
//    modifier: Modifier = Modifier,
//    contentPadding: PaddingValues = PaddingValues(0.dp), // add navController parameter
//    navController: NavController
//) {
//    var searchQuery by remember { mutableStateOf("") }
//    //conditional to avoid showing the searchbar
////    var isOptionsScreen by remember { mutableStateOf(false) }
////    var isPokemonInfoScren by remember { mutableStateOf(false) }
//
//    val focusManager = LocalFocusManager.current
//
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        // Place background outside the Column and NavHost
//        Box(modifier = Modifier.fillMaxSize()
//            .background(Color.Black.copy(0.6f))) {
//
//            Image(
//                painter = painterResource(id = R.drawable.pikachu_4k),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                alpha = 0.5f
//            )
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(contentPadding)
//        ) {
//            SearchBarCard(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = "Search here",
//                onTypeSelected = {},
//                modifier = Modifier
//                    .background(Color.Black)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            when (marsUiState) {
//                is MarsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
//                is MarsUiState.Success -> {
//                    val filteredPokemons = marsUiState.photos.filter { pokemon ->
//                        pokemon.name.contains(searchQuery, ignoreCase = true)
//                    }
//                    PhotosGridScreen(
//
//                        pokemons = filteredPokemons,
//                        contentPadding = contentPadding,
//                        modifier = Modifier.fillMaxSize(),
//                        onPokemonClick = { pokemon ->
//                            val pokemonId = pokemon.url.split("/").dropLast(1).last()
//                            // passign a id to another screen
////                            navController.navigate("${PokemonScreen.Info.name}/$pokemonId")
//                        }
//                    )
//                }
//                is MarsUiState.Error -> ErrorScreen(retryAction, modifier = Modifier.fillMaxSize())
//            }
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//                PressableImage(
//                    R.drawable.entry_ball,
//                    "Entry ball",
//                    onClick = { navController.navigate(PokemonScreen.Options.name) },
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//        }
//    }
//}
//
//
//
///**
// * The home screen displaying the loading message.
// */
//@Composable
//fun LoadingScreen(modifier: Modifier = Modifier) {
//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
//}
//
///**
// * The home screen displaying error message with re-attempt button.
// */
//@Composable
//fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
//        )
//        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
//        Button(onClick = retryAction) {
//            Text(stringResource(R.string.retry))
//        }
//    }
//}
//
///**
// * The home screen displaying photo grid.
// */
//@Composable
//fun PhotosGridScreen(
//    pokemons: List<Pokemon>,
//    modifier: Modifier = Modifier,
//    contentPadding: PaddingValues = PaddingValues(0.dp),
//    onPokemonClick: (Pokemon) -> Unit
//) {
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(150.dp),
//        modifier = modifier.padding(horizontal = 4.dp),
//        contentPadding = contentPadding,
//    ) {
//        items(items = pokemons, key = { pokemon -> pokemon.name }) { pokemon ->
//            PokemonCard(pokemon, modifier = Modifier.padding(4.dp), onClick = { onPokemonClick(pokemon) })
//        }
//    }
//}
//
//@Composable
//fun PokemonCard(pokemon: Pokemon, modifier: Modifier = Modifier, onClick: () -> Unit) {
//    val pokemonId = pokemon.url.split("/").dropLast(1).last()
//    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
//
//    Card(
//        modifier = modifier
//            .aspectRatio(1f)
//            .clickable { onClick() },
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
//    ) {
//        AsyncImage(
//            model = imageUrl,
//            contentDescription = pokemon.name,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Fit
//        )
//        Text(
//            text = pokemon.name.capitalize(),
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(8.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LoadingScreenPreview() {
//    MarsPhotosTheme {
//        LoadingScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ErrorScreenPreview() {
//    MarsPhotosTheme {
//        ErrorScreen({}) // Pass an empty retry action
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PhotosGridScreenPreview() {
//    MarsPhotosTheme {
//        val mockData = List(10) { Pokemon("$it", "") }
//        PhotosGridScreen(
//            mockData,
//            onPokemonClick = TODO()
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val navController = rememberNavController() // Create a navController for preview
//    HomeScreen(
//        marsUiState = MarsUiState.Success(listOf(Pokemon("bulbasaur", "url"))),
//        retryAction = {},
//        navController = navController
//    )
//}
