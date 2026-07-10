package net.uk.rodgers.beatslist.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "set_list_songs",
    primaryKeys = ["setListId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = SetList::class,
            parentColumns = ["id"],
            childColumns = ["setListId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Song::class,
            parentColumns = ["id"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("setListId"), Index("songId")]
)
data class SetListSongCrossRef(
    val setListId: Long,
    val songId: Long,
    val position: Int // To maintain order in the set list
)
