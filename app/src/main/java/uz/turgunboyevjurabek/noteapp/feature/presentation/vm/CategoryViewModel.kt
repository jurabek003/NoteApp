package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories: StateFlow<List<MyCategory>> = categoryRepository.getAllCategories()
        .stateIn(viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    fun addCategory(myCategory: MyCategory){
        viewModelScope.launch {
            categoryRepository.insertCategory(myCategory)
        }
    }
}
