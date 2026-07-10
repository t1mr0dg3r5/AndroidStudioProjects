package net.uk.rodgers.beatslist.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.SinglePaneSceneStrategy
import androidx.navigation3.ui.NavDisplay
import net.uk.rodgers.beatslist.data.BeatsRepository
import net.uk.rodgers.beatslist.ui.home.HomeScreen
import net.uk.rodgers.beatslist.ui.setlists.SetListEditorScreen
import net.uk.rodgers.beatslist.ui.setlists.SetListListScreen
import net.uk.rodgers.beatslist.ui.setlists.SetListViewerScreen
import net.uk.rodgers.beatslist.ui.setlists.SetListViewModel
import net.uk.rodgers.beatslist.ui.songs.SongEditorScreen
import net.uk.rodgers.beatslist.ui.songs.SongListScreen
import net.uk.rodgers.beatslist.ui.songs.SongViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavGraph(
    backStack: List<NavKey>,
    repository: BeatsRepository,
    onNavigate: (Destination) -> Unit,
    onBack: () -> Unit
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

    val entryProvider = remember {
        entryProvider<NavKey> {
            entry<Destination.Home> {
                HomeScreen(
                    onNavigateToSongs = { onNavigate(Destination.SongList) },
                    onNavigateToSetLists = { onNavigate(Destination.SetListList) }
                )
            }

            // Song Library Adaptive
            entry<Destination.SongList>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = {
                        AdaptivePlaceholder("Select a song to view or edit.")
                    }
                )
            ) {
                val songViewModel: SongViewModel = viewModel(factory = SongViewModelFactory(repository))
                SongListScreen(
                    viewModel = songViewModel,
                    onAddSong = { onNavigate(Destination.SongEditor()) },
                    onEditSong = { id -> onNavigate(Destination.SongEditor(id)) }
                )
            }
            entry<Destination.SongEditor>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { key ->
                val songViewModel: SongViewModel = viewModel(factory = SongViewModelFactory(repository))
                SongEditorScreen(
                    viewModel = songViewModel,
                    songId = key.songId,
                    onBack = onBack
                )
            }

            // Set List Adaptive
            entry<Destination.SetListList>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = {
                        AdaptivePlaceholder("Select a set list to manage or perform.")
                    }
                )
            ) {
                val setListViewModel: SetListViewModel = viewModel(factory = SetListViewModelFactory(repository))
                SetListListScreen(
                    viewModel = setListViewModel,
                    onAddSetList = { onNavigate(Destination.SetListEditor()) },
                    onEditSetList = { id -> onNavigate(Destination.SetListEditor(id)) },
                    onPerformSetList = { id -> onNavigate(Destination.SetListViewer(id)) }
                )
            }
            entry<Destination.SetListEditor>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { key ->
                val setListViewModel: SetListViewModel = viewModel(factory = SetListViewModelFactory(repository))
                SetListEditorScreen(
                    viewModel = setListViewModel,
                    setListId = key.setListId,
                    onBack = onBack
                )
            }
            entry<Destination.SetListViewer>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { key ->
                val setListViewModel: SetListViewModel = viewModel(factory = SetListViewModelFactory(repository))
                SetListViewerScreen(
                    viewModel = setListViewModel,
                    setListId = key.setListId,
                    onBack = onBack
                )
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = onBack,
        sceneStrategy = listDetailStrategy then SinglePaneSceneStrategy(),
        entryProvider = entryProvider
    )
}

@Composable
fun AdaptivePlaceholder(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

class SongViewModelFactory(private val repository: BeatsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SetListViewModelFactory(private val repository: BeatsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SetListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
