package uz.turgunboyevjurabek.noteapp.feature.domein.use_case

import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note){
        noteRepository.insertNote(note)
    }


}