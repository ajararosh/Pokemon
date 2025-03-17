package com.example.marsphotos.test.uitesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marsphotos.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

@Composable
fun PokemonInfoScreen(pokemonId: String, onBackClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    var pokemonDetail by remember { mutableStateOf<PokemonDetail?>(null) }
    var description by remember { mutableStateOf("Loading...") }

    LaunchedEffect(pokemonId) {
        scope.launch {
            try {
                val pokemon = withContext(Dispatchers.IO) { apiService.getPokemonDetails(pokemonId) }
                val speciesInfo = withContext(Dispatchers.IO) { apiService.getPokemonSpecies(pokemonId) }

                pokemonDetail = pokemon
                description = speciesInfo.flavorTextEntries
                    .firstOrNull { it.language.name == "en" }
                    ?.text
                    ?.replace("\n", " ")
                    ?: "No description available"

            } catch (e: Exception) {
                description = "Error: ${e.message}"
            }
        }
    }

    pokemonDetail?.let { pokemon ->
        Box(modifier = Modifier.fillMaxSize()
            .background(Color.White)) {
            Image(
                painter = painterResource(R.drawable.pikachu_4k),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.7f
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .offset(y = 40.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .border(1.dp, Color.Black, shape = CircleShape)
                    .clickable { onBackClick() },
                tint = Color.Black
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png",
                    contentDescription = "Pokemon Image",
                    modifier = Modifier.size(200.dp)
                )

                Text(
                    text = pokemon.name.capitalize(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoField(label = "Height", value = "${pokemon.height / 10.0} m")
                        InfoField(label = "Weight", value = "${pokemon.weight / 10.0} kg")
                        InfoField(label = "Base Exp", value = "${pokemon.baseExperience}")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Description: $description",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Loading...", modifier = Modifier.align(Alignment.Center))
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .clickable { onBackClick() }
                    .offset(y = 40.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .border(1.dp, Color.Black, shape = CircleShape),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun InfoField(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White,
            modifier = Modifier.background(color = Color.Black).alpha(0.8f))
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

// Data models and Retrofit setup (apiService)
@Serializable
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @SerialName("base_experience") val baseExperience: Int,
    val types: List<PokemonTypeWrapper>,
    val species: PokemonSpeciesInfo
)

@Serializable
data class PokemonTypeWrapper(
    val type: PokemonType
)

@Serializable
data class PokemonType(
    val name: String
)

@Serializable
data class PokemonSpeciesInfo(
    val url: String
)

@Serializable
data class PokemonSpeciesResponse(
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

@Serializable
data class FlavorTextEntry(
    @SerialName("flavor_text") val text: String,
    val language: Language
)

@Serializable
data class Language(
    val name: String
)

val json = Json { ignoreUnknownKeys = true }

val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService = retrofit.create(TestPokeApiService::class.java)

interface TestPokeApiService {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: String): PokemonDetail

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): PokemonSpeciesResponse
}

