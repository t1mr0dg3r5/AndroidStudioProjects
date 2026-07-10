package net.uk.rodgers.beatslist.data

import kotlinx.coroutines.flow.Flow
import net.uk.rodgers.beatslist.data.dao.SetListDao
import net.uk.rodgers.beatslist.data.dao.SongDao
import net.uk.rodgers.beatslist.data.entity.SetList
import net.uk.rodgers.beatslist.data.entity.SetListSongCrossRef
import net.uk.rodgers.beatslist.data.entity.Song

class BeatsRepository(
    private val songDao: SongDao,
    private val setListDao: SetListDao
) {
    // Songs
    val allSongs: Flow<List<Song>> = songDao.getAllSongsPaged()

    suspend fun getSongById(id: Long): Song? = songDao.getSongById(id)

    suspend fun insertSong(song: Song) = songDao.insertSong(song)

    suspend fun updateSong(song: Song) = songDao.updateSong(song)

    suspend fun deleteSong(song: Song) = songDao.deleteSong(song)

    // SetLists
    val allSetLists: Flow<List<SetList>> = setListDao.getAllSetLists()

    suspend fun insertSetList(setList: SetList) = setListDao.insertSetList(setList)

    suspend fun updateSetList(setList: SetList) = setListDao.updateSetList(setList)

    suspend fun deleteSetList(setList: SetList) = setListDao.deleteSetList(setList)

    suspend fun addSongToSetList(setListId: Long, songId: Long, position: Int) {
        setListDao.insertSetListSongCrossRef(SetListSongCrossRef(setListId, songId, position))
    }

    suspend fun updateSetListSongs(setListId: Long, songIds: List<Long>) {
        setListDao.deleteSongsFromSetList(setListId)
        songIds.forEachIndexed { index, songId ->
            setListDao.insertSetListSongCrossRef(SetListSongCrossRef(setListId, songId, index))
        }
    }

    fun getSongsForSetList(setListId: Long): Flow<List<Song>> = setListDao.getSongsForSetList(setListId)
}
