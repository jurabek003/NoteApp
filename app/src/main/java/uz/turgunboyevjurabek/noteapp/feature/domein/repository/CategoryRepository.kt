package uz.turgunboyevjurabek.noteapp.feature.domein.repository

import kotlinx.coroutines.flow.Flow
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory

interface CategoryRepository {

    fun getAllCategories(): Flow<List<MyCategory>>
    suspend fun insertCategory(category: MyCategory)

}