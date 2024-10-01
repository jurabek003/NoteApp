package uz.turgunboyevjurabek.noteapp.feature.domein.use_case

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val noteRepository: NoteRepository) {
     operator fun invoke(noteId:Int):Flow<Note?>{
        return noteRepository.getNoteById(noteId)
    }
}