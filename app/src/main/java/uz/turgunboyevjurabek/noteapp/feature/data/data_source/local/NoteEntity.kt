package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(
    tableName = "MyNotes",
    foreignKeys = [
        ForeignKey(
            entity = MyCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])] // categoryId ustunini indekslashtirish
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    var isDelete: Boolean = false,
    val categoryId: Int // Tanlangan kategoriya bilan bog'lanish
)
