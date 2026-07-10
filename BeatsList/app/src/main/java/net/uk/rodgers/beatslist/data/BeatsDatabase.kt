package net.uk.rodgers.beatslist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.uk.rodgers.beatslist.data.dao.SetListDao
import net.uk.rodgers.beatslist.data.dao.SongDao
import net.uk.rodgers.beatslist.data.entity.SetList
import net.uk.rodgers.beatslist.data.entity.SetListSongCrossRef
import net.uk.rodgers.beatslist.data.entity.Song

@Database(
    entities = [Song::class, SetList::class, SetListSongCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class BeatsDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun setListDao(): SetListDao

    companion object {
        @Volatile
        private var INSTANCE: BeatsDatabase? = null

        fun getDatabase(context: Context): BeatsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeatsDatabase::class.java,
                    "beats_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
