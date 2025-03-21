package com.example.marsphotos.test.codetest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.marsphotos.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingSearchBarScreen() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) } // Toggle visibility
    val skyBlue = Color(0xFF1F81BE)
    val pastelblue = Color(0xffb2c5ff)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                LargeTopAppBar(
                    title = {
                        val collapsedFraction = scrollBehavior.state.collapsedFraction

                        // Only show content when not fully collapsed
                        if (collapsedFraction < 0.99) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                                    .graphicsLayer {
                                        // Fade out as we scroll
                                        alpha = 1f - collapsedFraction
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Pokémon",
                                    modifier = Modifier.align(Alignment.Start),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.White
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.pokemon_logo),
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.CenterHorizontally)
                                        .border(2.dp,Color.Black, CircleShape),
                                    alignment = Alignment.Center
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = pastelblue,
                        scrolledContainerColor = Color.Black,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { showSearchBar = !showSearchBar }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    },
                    expandedHeight = 300.dp
                )

                // Search Bar
                if (showSearchBar) {
                    SearchBarCard(
                        value = searchQuery,
                        onValueChange = {searchQuery = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Search here...",
                        onTypeSelected = {}
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            items(50) { index ->
                ListItem(
                    headlineContent = { Text("Pokémon $index") },
                    leadingContent = {
                        Icon(Icons.Default.Star, contentDescription = null)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCollapsingSearchBarScreen() {
    CollapsingSearchBarScreen()
}
