package com.example.marsphotos.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.PokemonPhotosApplication
import com.example.marsphotos.data.PokemonRepository
import com.example.marsphotos.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: List<Pokemon>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: PokemonRepository) : ViewModel() {

    // Using MutableStateFlow instead of mutableStateOf
    private val _marsUiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
    val marsUiState: StateFlow<MarsUiState> = _marsUiState.asStateFlow()

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            _marsUiState.value = MarsUiState.Loading
            _marsUiState.value = try {
                MarsUiState.Success(marsPhotosRepository.getPokemonList())
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository)
            }
        }
    }
}
