package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.DeleteNoteUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.GetAllNotesUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.InsertNoteUseCase
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
):ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes


    init {
        viewModelScope.launch {
            getAllNotesUseCase().collect { notes ->
                _notes.value = notes
            }
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            insertNoteUseCase(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }
}