package com.example.marsphotos.test.codetest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.marsphotos.R

/*
* Todo:
*  1. Another submenu
*  2. Apply navigation to chips or cards
*  3.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCard(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onTypeSelected: (String) -> Unit
) {
    val goldenrodColor: Color = colorResource(id = R.color.goldenrod_yellow)
    val slateBlueGray: Color = colorResource(id = R.color.slate_blue_gray)

    var showMenu by remember { mutableStateOf(false) }
    var showAllTypes by remember { mutableStateOf(false) }
    var selectedTypes by remember { mutableStateOf(setOf<String>()) }

    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Column(modifier = modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures {
                // Don't clear focus if already focused
                focusManager.clearFocus()
            }
        }
    ) {
        // 🔹 Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused }, // Track focus state
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
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
                    focusedContainerColor = goldenrodColor.copy(alpha = 0.7f),  // 🔹 Highlighted when focused
                    unfocusedContainerColor = if (isFocused) goldenrodColor.copy(alpha = 0.7f) else slateBlueGray.copy(alpha = 0.5f), // 🔹 Reverts when unfocused
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    tint = goldenrodColor
                )
            }
        }

        // 🔹 Type Selector Card
        if (showMenu) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { showAllTypes = !showAllTypes }
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_drop),
                        contentDescription = "Expand Types",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Types",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (showAllTypes) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Toggle Types"
                    )
                }
            }
        }else{
            // Ensure to close submenu if they are still vissible
            showAllTypes = false
        }

        // 🔹 Type Selection
        if (showAllTypes) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                items(pokemonTypes) { type ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                selectedTypes = if (selectedTypes.contains(type.name)) {
                                    selectedTypes - type.name
                                } else {
                                    selectedTypes + type.name
                                }
                                onTypeSelected(type.name)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedTypes.contains(type.name)) goldenrodColor.copy(
                                alpha = 0.7f
                            ) else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(text = type.name, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun PreviewSearchBarCar() {
    val sampleText = remember { mutableStateOf("") }
    SearchBarCard(
        value = sampleText.value,
        onValueChange = { sampleText.value = it },
        placeholder = "Search here",
        modifier = Modifier,
        onTypeSelected = { /* Handle type selection */ }
    )
}