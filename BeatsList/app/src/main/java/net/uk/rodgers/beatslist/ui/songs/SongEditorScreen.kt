package net.uk.rodgers.beatslist.ui.songs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongEditorScreen(
    viewModel: SongViewModel,
    songId: Long?,
    onBack: () -> Unit
) {
    val song by viewModel.currentSong.collectAsState()

    var title by remember { mutableStateOf("") }
    var lyrics by remember { mutableStateOf("") }
    var chords by remember { mutableStateOf("") }

    LaunchedEffect(songId) {
        if (songId != null) {
            viewModel.loadSong(songId)
        } else {
            viewModel.clearCurrentSong()
        }
    }

    LaunchedEffect(song) {
        song?.let {
            title = it.title
            lyrics = it.lyrics
            chords = it.chords
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (songId == null) "Add Song" else "Edit Song") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveSong(title, lyrics, chords, songId)
                        onBack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = chords,
                onValueChange = { chords = it },
                label = { Text("Chords") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. C G Am F") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = lyrics,
                onValueChange = { lyrics = it },
                label = { Text("Lyrics") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 10
            )
        }
    }
}
