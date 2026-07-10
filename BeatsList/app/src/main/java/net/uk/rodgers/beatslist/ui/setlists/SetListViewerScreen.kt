package net.uk.rodgers.beatslist.ui.setlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.uk.rodgers.beatslist.data.entity.Song
import net.uk.rodgers.beatslist.ui.setlists.SetListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetListViewerScreen(
    viewModel: SetListViewModel,
    setListId: Long,
    onBack: () -> Unit
) {
    val setList by viewModel.currentSetList.collectAsState()
    val songs by viewModel.setListSongs.collectAsState()

    var selectedSongIndex by remember { mutableStateOf(0) }

    LaunchedEffect(setListId) {
        viewModel.loadSetList(setListId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(setList?.name ?: "Performance") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Sidebar for song selection
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxSize()
            ) {
                itemsIndexed(songs) { index, song ->
                    ListItem(
                        modifier = Modifier.clickable { selectedSongIndex = index },
                        headlineContent = { 
                            Text(
                                text = "${index + 1}. ${song.title}",
                                style = if (index == selectedSongIndex) 
                                    MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold) 
                                else 
                                    MaterialTheme.typography.titleMedium
                            ) 
                        },
                        colors = if (index == selectedSongIndex)
                            ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        else
                            ListItemDefaults.colors()
                    )
                    HorizontalDivider()
                }
            }

            // Main viewer for lyrics and chords
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (songs.isNotEmpty() && selectedSongIndex < songs.size) {
                    val song = songs[selectedSongIndex]
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Chords:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = song.chords,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Lyrics:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = song.lyrics,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Text("No songs in this set list.")
                }
            }
        }
    }
}
