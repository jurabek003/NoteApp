package uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note

import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteByCategoryIdUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(categoryId: Int) {
        noteRepository.deleteNotesByCategoryId(categoryId)
    }

}