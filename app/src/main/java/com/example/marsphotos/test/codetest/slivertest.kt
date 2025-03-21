package com.example.marsphotos.test.codetest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopAppBar(
    title: String = "Search",
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = { /* Handle navigation icon click */ }
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    // Create a filtered list based on the search query
    val allItems = List(50) { "Item $it" }
    val filteredItems = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            allItems
        } else {
            allItems.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    // Handle item click
    val onItemClick: (String) -> Unit = { item ->
        if (isSearchVisible) {
            // If search is active, selecting an item will apply it as search term
            searchQuery = item
            isSearchVisible = false
        } else {
            // If normal browsing, just select the item
            selectedItem = item
            // Here you would typically navigate to item details or perform some action
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        // Only show back arrow when search is visible
                        if (isSearchVisible) {
                            IconButton(onClick = {
                                isSearchVisible = false
                                searchQuery = "" // Clear search when exiting
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            isSearchVisible = !isSearchVisible
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                // Animated search field that appears below the app bar
                AnimatedVisibility(
                    visible = isSearchVisible,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = { Text("Search items...") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear search")
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Main content with filtered results
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable { if (isSearchVisible) isSearchVisible = false },
            contentPadding = PaddingValues(16.dp)
        ) {
            items(filteredItems) { item ->
                val isSelected = item == selectedItem

                ListItem(
                    headlineContent = {
                        Text(
                            text = item,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    modifier = Modifier.clickable { onItemClick(item) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchableTopAppBarPreview() {
    MaterialTheme {
        Surface {
            SearchableTopAppBar()
        }
    }
}