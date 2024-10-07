package uz.turgunboyevjurabek.noteapp.feature.domein.repository

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>
    fun getNoteById(noteId:Int):Flow<Note?>
    fun getIsDeletesNotes(boolean: Boolean):Flow<List<Note?>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNoteById(noteId: Int)
    fun getNotesByCategoryId(categoryId: Int, isDelete: Boolean): Flow<List<Note>>
    suspend fun deleteNotesByCategoryId(categoryId: Int)

}