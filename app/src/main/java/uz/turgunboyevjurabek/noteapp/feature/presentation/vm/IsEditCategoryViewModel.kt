package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class IsEditCategoryViewModel() : ViewModel(){
    private val _isEditCategory= MutableStateFlow(false)
    val isEditCategory=_isEditCategory.asStateFlow()

    fun setIsEditCategory(boolean: Boolean){
        _isEditCategory.value=boolean
    }
}