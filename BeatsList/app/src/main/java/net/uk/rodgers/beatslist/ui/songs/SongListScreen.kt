package net.uk.rodgers.beatslist.ui.songs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.uk.rodgers.beatslist.data.entity.Song
import net.uk.rodgers.beatslist.ui.theme.BeatsListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListScreen(
    viewModel: SongViewModel,
    onAddSong: () -> Unit,
    onEditSong: (Long) -> Unit
) {
    val songs by viewModel.allSongs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Song Library") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSong) {
                Icon(Icons.Default.Add, contentDescription = "Add Song")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(songs) { song ->
                SongItem(
                    song = song,
                    onClick = { onEditSong(song.id) },
                    onDelete = { viewModel.deleteSong(song) }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(song.title) },
        supportingContent = {
            Text(
                text = song.lyrics.take(50).replace("\n", " ") + "...",
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingContent = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Song")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SongItemPreview() {
    BeatsListTheme {
        SongItem(
            song = Song(1, "Sample Song", "Lyrics line 1\nLyrics line 2", "C G Am F"),
            onClick = {},
            onDelete = {}
        )
    }
}
