package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

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

    @Query("select * from MyNotes where id = :noteId")
    fun getNoteByIdFlow(noteId: Int): Flow<NoteEntity?>

    @Query("select * from MyNotes where isDelete = :isDelete")
    fun getIsDeletesNote(isDelete: Boolean):Flow<List<NoteEntity?>>

    @Query("delete from MyNotes where isDelete = :isDelete")
    suspend fun deleteAllDeletesNotes(isDelete: Boolean)

    @Query("delete from MyNotes where id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("select * from MyNotes where categoryId = :categoryId and isDelete = :isDelete")
    fun getNotesByCategoryId(categoryId: Int, isDelete: Boolean): Flow<List<NoteEntity>>

    @Query("delete from MyNotes where categoryId = :categoryId")
    suspend fun deleteNotesByCategoryId(categoryId: Int)

//    @Query("select * from MyNotes where categoryId = :categoryId and isDelete = :isDelete")
//    fun getNotesByCategoryIdAndIsDelete(categoryId: Int, isDelete: Boolean): Flow<List<NoteEntity>>


}