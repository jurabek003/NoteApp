package uz.turgunboyevjurabek.noteapp.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.CategoryDao
import uz.turgunboyevjurabek.noteapp.feature.data.mappers.toCategory
import uz.turgunboyevjurabek.noteapp.feature.data.mappers.toCategoryEntity
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository

class CategoryRepositoryImpl(private val categoryDao: CategoryDao): CategoryRepository {

    override fun getAllCategories(): Flow<List<MyCategory>> {
        return categoryDao.getAllCategories().map { categoryEntities ->
            categoryEntities.map { it.toCategory() }
        }
    }

    override suspend fun insertCategory(category: MyCategory) {
        categoryDao.insertCategory(category.toCategoryEntity())
    }

}