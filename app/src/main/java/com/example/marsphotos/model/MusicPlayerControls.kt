package com.example.marsphotos.model

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.example.marsphotos.R

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    private var mediaPlayer: MediaPlayer? = null

    fun start() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplication(), R.raw.pokemon_song)
            mediaPlayer?.isLooping = true
        }
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        stop()
    }
}