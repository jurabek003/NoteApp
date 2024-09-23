package uz.turgunboyevjurabek.noteapp.feature.domein.repository

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)

}