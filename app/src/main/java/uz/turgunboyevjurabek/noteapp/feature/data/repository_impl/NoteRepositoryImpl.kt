package uz.turgunboyevjurabek.noteapp.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.NoteDao
import uz.turgunboyevjurabek.noteapp.feature.data.mappers.toNote
import uz.turgunboyevjurabek.noteapp.feature.data.mappers.toNoteEntity
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map {noteEntities ->
            noteEntities.map { it.toNote() }
        }
    }

    override fun getNoteById(noteId: Int): Flow<Note?> {
        return noteDao.getNoteByIdFlow(noteId).map { noteEntities->
            noteEntities?.toNote()
        }
    }

    override fun getIsDeletesNotes(boolean: Boolean): Flow<List<Note?>>{
        return noteDao.getIsDeletesNote(isDelete = boolean).map {noteEntities->
            noteEntities.map { it?.toNote() }
        }
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.editNote(note.toNoteEntity())
    }

}