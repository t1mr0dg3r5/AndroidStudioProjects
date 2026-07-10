package net.uk.rodgers.beatslist.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Destination : NavKey {
    @Serializable
    data object Home : Destination()

    @Serializable
    data object SongList : Destination()

    @Serializable
    data class SongEditor(val songId: Long? = null) : Destination()

    @Serializable
    data object SetListList : Destination()

    @Serializable
    data class SetListEditor(val setListId: Long? = null) : Destination()

    @Serializable
    data class SetListViewer(val setListId: Long) : Destination()
}
