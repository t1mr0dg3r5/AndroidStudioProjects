package net.uk.rodgers.beatslist.ui.setlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
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
import net.uk.rodgers.beatslist.data.entity.SetList
import net.uk.rodgers.beatslist.ui.setlists.SetListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetListListScreen(
    viewModel: SetListViewModel,
    onAddSetList: () -> Unit,
    onEditSetList: (Long) -> Unit,
    onPerformSetList: (Long) -> Unit
) {
    val setLists by viewModel.allSetLists.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Set Lists") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSetList) {
                Icon(Icons.Default.Add, contentDescription = "Add Set List")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(setLists) { setList ->
                ListItem(
                    modifier = Modifier.clickable { onEditSetList(setList.id) },
                    headlineContent = { Text(setList.name) },
                    trailingContent = {
                        IconButton(onClick = { onPerformSetList(setList.id) }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Perform")
                        }
                    },
                    leadingContent = {
                        IconButton(onClick = { viewModel.deleteSetList(setList) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
