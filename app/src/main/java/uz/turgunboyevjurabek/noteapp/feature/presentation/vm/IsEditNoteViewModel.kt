package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IsEditNoteViewModel():ViewModel() {
    private var _isEditNote = MutableStateFlow(false)
    var isEditNote: StateFlow<Boolean> = _isEditNote.asStateFlow()


    fun setIsEditNote(isEditNote: Boolean) {
        _isEditNote.value = isEditNote
    }

}