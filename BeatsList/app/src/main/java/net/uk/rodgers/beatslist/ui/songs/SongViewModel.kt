package net.uk.rodgers.beatslist.ui.songs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.uk.rodgers.beatslist.data.BeatsRepository
import net.uk.rodgers.beatslist.data.entity.Song

class SongViewModel(private val repository: BeatsRepository) : ViewModel() {

    val allSongs: StateFlow<List<Song>> = repository.allSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    fun loadSong(id: Long) {
        viewModelScope.launch {
            _currentSong.value = repository.getSongById(id)
        }
    }

    fun saveSong(title: String, lyrics: String, chords: String, id: Long? = null) {
        viewModelScope.launch {
            val song = Song(
                id = id ?: 0,
                title = title,
                lyrics = lyrics,
                chords = chords
            )
            if (id == null || id == 0L) {
                repository.insertSong(song)
            } else {
                repository.updateSong(song)
            }
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            repository.deleteSong(song)
        }
    }

    fun clearCurrentSong() {
        _currentSong.value = null
    }
}
