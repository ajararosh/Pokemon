package com.example.marsphotos.test.uitesting

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.marsphotos.R


@SuppressLint("ResourceType")
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val goldenrodColor: Color = colorResource(id = R.color.goldenrod_yellow)
    val slate_blue_gray: Color = colorResource(id = R.color.slate_blue_gray)
    val a: Color = colorResource(id = R.color.charcoal_teal)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
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
        placeholder = "Search here"
    )

}
