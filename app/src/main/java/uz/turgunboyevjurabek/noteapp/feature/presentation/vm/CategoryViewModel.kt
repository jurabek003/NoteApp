package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category.AddCategoryUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category.DeleteCategoryUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category.GetAllCategoryUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category.GetCategoryByIdUseCase
import uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category.UpdateCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    private val _category = MutableStateFlow<List<MyCategory>>(emptyList())

    val myCategories: StateFlow<List<MyCategory>> =_category.asStateFlow()

    private val _categoryById = MutableStateFlow<MyCategory?>(null)
    val categoryById: StateFlow<MyCategory?> = _categoryById.asStateFlow()


    init {
        viewModelScope.launch {
            getAllCategoryUseCase().collect{ allCategory ->
                _category.value=allCategory
            }
        }
    }

    fun getCategoryById(myCategory: MyCategory){
        viewModelScope.launch {
            getCategoryByIdUseCase(myCategory).collectLatest { result->
                _categoryById.value=result
            }
        }
    }

     fun deleteCategory(myCategory: MyCategory){
         viewModelScope.launch {
             deleteCategoryUseCase(myCategory)
         }
    }

    fun updateCategory(myCategory: MyCategory){
        viewModelScope.launch {
            updateCategoryUseCase(myCategory)
        }
    }

    fun addCategory(myCategory: MyCategory){
        viewModelScope.launch {
            addCategoryUseCase(myCategory = myCategory)
        }
    }

}
