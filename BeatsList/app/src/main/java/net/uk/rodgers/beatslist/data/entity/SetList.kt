package net.uk.rodgers.beatslist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_lists")
data class SetList(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
