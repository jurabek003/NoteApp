package uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository
import javax.inject.Inject

class GetNotesByCategoryIdUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke(categoryId: Int, isDelete: Boolean): Flow<List<Note?>> {
     return  noteRepository.getNotesByCategoryId(categoryId,isDelete)
    }
}