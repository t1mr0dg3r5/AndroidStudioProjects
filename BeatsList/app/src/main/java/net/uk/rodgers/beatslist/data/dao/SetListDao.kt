package net.uk.rodgers.beatslist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import net.uk.rodgers.beatslist.data.entity.SetList
import net.uk.rodgers.beatslist.data.entity.SetListSongCrossRef
import net.uk.rodgers.beatslist.data.entity.Song

@Dao
interface SetListDao {
    @Query("SELECT * FROM set_lists ORDER BY name ASC")
    fun getAllSetLists(): Flow<List<SetList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetList(setList: SetList): Long

    @Update
    suspend fun updateSetList(setList: SetList)

    @Delete
    suspend fun deleteSetList(setList: SetList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetListSongCrossRef(crossRef: SetListSongCrossRef)

    @Query("DELETE FROM set_list_songs WHERE setListId = :setListId")
    suspend fun deleteSongsFromSetList(setListId: Long)

    @Transaction
    @Query("""
        SELECT songs.* FROM songs 
        JOIN set_list_songs ON songs.id = set_list_songs.songId 
        WHERE set_list_songs.setListId = :setListId 
        ORDER BY set_list_songs.position ASC
    """)
    fun getSongsForSetList(setListId: Long): Flow<List<Song>>
}
