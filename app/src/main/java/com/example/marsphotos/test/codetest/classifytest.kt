//package com.example.marsphotos.test.codetest
//
//import com.example.marsphotos.test.codetest.pokemonTypes
//
//val pokemonTypes = listOf(
//    PokemonType("Normal"),
//    PokemonType("Fighting"),
//    PokemonType("Flying"),
//    PokemonType("Poison"),
//    PokemonType("Ground"),
//    PokemonType("Rock"),
//    PokemonType("Bug"),
//    PokemonType("Ghost"),
//    PokemonType("Steel"),
//    PokemonType("Fire"),
//    PokemonType("Water"),
//    PokemonType("Grass"),
//    PokemonType("Electric"),
//    PokemonType("Psychic"),
//    PokemonType("Ice"),
//    PokemonType("Dragon"),
//    PokemonType("Dark"),
//    PokemonType("Fairy")
//)
//
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.example.marsphotos.R // Replace with your R file
//
//@Composable
//fun TypeFilterButton(
//    onTypeSelected: (String) -> Unit // Callback for type selection
//) {
//    var showTypes by remember { mutableStateOf(false) }
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        // Button
//        IconButton(onClick = { showTypes = !showTypes }) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_filter), // Replace with your image
//                contentDescription = "Filter by Type",
//                modifier = Modifier.size(48.dp) // Adjust size as needed
//            )
//        }
//
//        // Chips (if showTypes is true)
//        if (showTypes) {
//            Column(
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .verticalScroll(rememberScrollState())
//            ) {
//                pokemonTypes.forEach { type ->
//                    FilterChip(
//                        selected = false,
//                        onClick = {
//                            onTypeSelected(type.name)
//                            showTypes = false // Hide chips after selection
//                        },
//                        label = { Text(type.name) },
//                        modifier = Modifier.padding(4.dp)
//                    )
//                }
//            }
//        }
//    }
//}