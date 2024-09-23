package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("select *from MyNotes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun editNote(noteEntity: NoteEntity)

}