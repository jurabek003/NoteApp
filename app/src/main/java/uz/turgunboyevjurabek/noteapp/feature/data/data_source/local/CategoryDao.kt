package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("select * from categories")
    fun getAllCategories(): Flow<List<MyCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: MyCategoryEntity)

    @Query("select * from categories where id = :categoryId")
    fun getCategoryById(categoryId:Int):Flow<MyCategoryEntity?>

    @Update
    suspend fun updateCategory(category: MyCategoryEntity)

    @Delete
    suspend fun deleteCategory(category: MyCategoryEntity)

}
