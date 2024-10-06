package uz.turgunboyevjurabek.noteapp.feature.domein.repository

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory

interface CategoryRepository {

    fun getAllCategories(): Flow<List<MyCategory>>
    suspend fun insertCategory(category: MyCategory)
    fun getCategoryByID(myCategory: MyCategory):Flow<MyCategory?>
    suspend fun deleteCategory(myCategory: MyCategory)
    suspend fun updateCategory(myCategory: MyCategory)

}