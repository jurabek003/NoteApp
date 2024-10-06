package uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    operator fun invoke(myCategory: MyCategory):Flow<MyCategory?>{
        return categoryRepository.getCategoryByID(myCategory)
    }
}