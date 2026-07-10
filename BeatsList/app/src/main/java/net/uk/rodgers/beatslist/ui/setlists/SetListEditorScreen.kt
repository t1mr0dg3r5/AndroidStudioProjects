package net.uk.rodgers.beatslist.ui.setlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.uk.rodgers.beatslist.data.entity.Song
import net.uk.rodgers.beatslist.ui.setlists.SetListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetListEditorScreen(
    viewModel: SetListViewModel,
    setListId: Long?,
    onBack: () -> Unit
) {
    val setList by viewModel.currentSetList.collectAsState()
    val songsInSetList by viewModel.setListSongs.collectAsState()
    val allSongs by viewModel.allSongs.collectAsState()

    var name by remember { mutableStateOf("") }
    val selectedSongs = remember { mutableStateListOf<Song>() }
    var showAddSongDialog by remember { mutableStateOf(false) }

    LaunchedEffect(setListId) {
        if (setListId != null) {
            viewModel.loadSetList(setListId)
        } else {
            viewModel.clearCurrentSetList()
        }
    }

    LaunchedEffect(setList) {
        setList?.let { name = it.name }
    }

    LaunchedEffect(songsInSetList) {
        selectedSongs.clear()
        selectedSongs.addAll(songsInSetList)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (setListId == null) "New Set List" else "Edit Set List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveSetList(name, selectedSongs.map { it.id }, setListId)
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
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Set List Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Songs", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showAddSongDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Song")
                }
            }
            LazyColumn {
                itemsIndexed(selectedSongs) { index, song ->
                    ListItem(
                        headlineContent = { Text(song.title) },
                        trailingContent = {
                            Row {
                                IconButton(
                                    onClick = {
                                        if (index > 0) {
                                            val temp = selectedSongs[index]
                                            selectedSongs[index] = selectedSongs[index - 1]
                                            selectedSongs[index - 1] = temp
                                        }
                                    },
                                    enabled = index > 0
                                ) {
                                    Icon(Icons.Default.ArrowUpward, contentDescription = "Move Up")
                                }
                                IconButton(
                                    onClick = {
                                        if (index < selectedSongs.size - 1) {
                                            val temp = selectedSongs[index]
                                            selectedSongs[index] = selectedSongs[index + 1]
                                            selectedSongs[index + 1] = temp
                                        }
                                    },
                                    enabled = index < selectedSongs.size - 1
                                ) {
                                    Icon(Icons.Default.ArrowDownward, contentDescription = "Move Down")
                                }
                                IconButton(onClick = { selectedSongs.removeAt(index) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                                }
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }

    if (showAddSongDialog) {
        val tempSelection = remember { mutableStateListOf<Long>().apply { addAll(selectedSongs.map { it.id }) } }
        AlertDialog(
            onDismissRequest = { showAddSongDialog = false },
            title = { Text("Select Songs") },
            text = {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(allSongs) { song ->
                        val id = song.id
                        val title = song.title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (tempSelection.contains(id)) tempSelection.remove(id)
                                    else tempSelection.add(id)
                                }
                        ) {
                            Checkbox(
                                checked = tempSelection.contains(id),
                                onCheckedChange = { checked ->
                                    if (checked) tempSelection.add(id)
                                    else tempSelection.remove(id)
                                }
                            )
                            Text(title)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updatedSongs = tempSelection.mapNotNull { id -> allSongs.find { it.id == id } }
                    selectedSongs.clear()
                    selectedSongs.addAll(updatedSongs)
                    showAddSongDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showAddSongDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
