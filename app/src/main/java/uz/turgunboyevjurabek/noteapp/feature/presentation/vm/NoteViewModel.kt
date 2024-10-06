package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.DeleteNoteUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.GetAllNotesUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.GetNoteByIdUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.InsertNoteUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.IsDeleteUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.note.UpdateNoteUseCase
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val getIsDeleteUseCase: IsDeleteUseCase
):ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    private val _isDeleteNote=MutableStateFlow<List<Note?>>(emptyList())
    val isDeleteNote=_isDeleteNote.asStateFlow()

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

    fun updateNote(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase.invoke(note)
        }
    }

    fun getNoteById(noteId:Int){
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).collectLatest {result->
                _note.value=result
            }
        }
    }

    fun getIsDeleteNotes(boolean: Boolean){
        viewModelScope.launch {
            getIsDeleteUseCase(boolean).collect{result->
                _isDeleteNote.value=result
            }
        }

    }


}