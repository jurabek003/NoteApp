package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class MyCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
