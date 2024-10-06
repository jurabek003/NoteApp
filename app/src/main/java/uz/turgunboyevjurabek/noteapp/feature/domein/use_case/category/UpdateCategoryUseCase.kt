package uz.turgunboyevjurabek.noteapp.feature.domein.use_case.category

import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(myCategory: MyCategory){
        categoryRepository.updateCategory(myCategory = myCategory)
    }

}