package com.example.marsphotos.test.codetest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.marsphotos.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons

data class PokemonType(val name: String)

val pokemonTypes = listOf(
    PokemonType("Normal"),
    PokemonType("Fighting"),
    PokemonType("Flying"),
    PokemonType("Poison"),
    PokemonType("Ground"),
    PokemonType("Rock"),
    PokemonType("Bug"),
    PokemonType("Ghost"),
    PokemonType("Steel"),
    PokemonType("Fire"),
    PokemonType("Water"),
    PokemonType("Grass"),
    PokemonType("Electric"),
    PokemonType("Psychic"),
    PokemonType("Ice"),
    PokemonType("Dragon"),
    PokemonType("Dark"),
    PokemonType("Fairy")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onTypeSelected: (String) -> Unit
) {
    val goldenrodColor: Color = colorResource(id = R.color.goldenrod_yellow)
    val slate_blue_gray: Color = colorResource(id = R.color.slate_blue_gray)
    var showTypes by remember { mutableStateOf(false) }
//    var selectedType by remember { mutableStateOf<String?>(null) } // Track selected type
    var selectedTypes by remember { mutableStateOf(setOf<String>()) } // Track selected types
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .padding(2.dp),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    },
                    trailingIcon = {
                        if (value.isNotEmpty()) {
                            IconButton(onClick = { onValueChange("") }) {
                                Icon(Icons.Filled.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    placeholder = { Text(placeholder) },
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedContainerColor = goldenrodColor.copy(alpha = 0.7f),
                        unfocusedContainerColor = slate_blue_gray.copy(alpha = 0.5f)
                    )
                )
                IconButton(onClick = { showTypes = !showTypes }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter",
                        tint = goldenrodColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 80.dp,
                    bottom = 150.dp
                )) {
                if (showTypes) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        pokemonTypes.forEach { type ->
                            FilterChip(
                                selected = selectedTypes.contains(type.name), // Highlight selected chip
                                onClick = {
                                    selectedTypes = if (selectedTypes.contains(type.name)) {
                                        selectedTypes - type.name // Deselect if already selected
                                    } else {
                                        selectedTypes + type.name // Select if not selected
                                    }
                                    onTypeSelected(type.name) // Pass the selected type to the parent
                                },
                                label = { Text(type.name) },
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    // Use mutableStateOf to create a mutable state
    val sampleText = remember { mutableStateOf("") } // Start with an empty string
    SearchBar(
        value = sampleText.value,
        onValueChange = { sampleText.value = it },
        placeholder = "Search here",
        modifier = Modifier,
        onTypeSelected = { /* Handle type selection */ }
    )
}
