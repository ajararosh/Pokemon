package com.example.marsphotos
//import com.example.marsphotos.model.AudioPlayerControls
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.marsphotos.test.newui.ScaffoldExample
import com.example.marsphotos.ui.theme.MarsPhotosTheme
//import com.example.marsphotos.test.uitesting.PokemonNavigationApp
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ScaffoldExample()
                }
            }
        }
    }
}
