package net.uk.rodgers.beatslist.ui.setlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.uk.rodgers.beatslist.data.BeatsRepository
import net.uk.rodgers.beatslist.data.entity.SetList
import net.uk.rodgers.beatslist.data.entity.Song

class SetListViewModel(private val repository: BeatsRepository) : ViewModel() {

    val allSetLists: StateFlow<List<SetList>> = repository.allSetLists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSongs: StateFlow<List<Song>> = repository.allSongs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentSetList = MutableStateFlow<SetList?>(null)
    val currentSetList: StateFlow<SetList?> = _currentSetList

    private val _setListSongs = MutableStateFlow<List<Song>>(emptyList())
    val setListSongs: StateFlow<List<Song>> = _setListSongs

    fun loadSetList(id: Long) {
        viewModelScope.launch {
            repository.allSetLists.collect { list ->
                _currentSetList.value = list.find { it.id == id }
            }
        }
        viewModelScope.launch {
            repository.getSongsForSetList(id).collect { songs ->
                _setListSongs.value = songs
            }
        }
    }

    fun saveSetList(name: String, songIds: List<Long>, id: Long? = null) {
        viewModelScope.launch {
            val setList = SetList(id = id ?: 0, name = name)
            val newId = if (id == null || id == 0L) {
                repository.insertSetList(setList)
            } else {
                repository.updateSetList(setList)
                id
            }
            repository.updateSetListSongs(newId, songIds)
        }
    }

    fun deleteSetList(setList: SetList) {
        viewModelScope.launch {
            repository.deleteSetList(setList)
        }
    }

    fun clearCurrentSetList() {
        _currentSetList.value = null
        _setListSongs.value = emptyList()
    }
}
