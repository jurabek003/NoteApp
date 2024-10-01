package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyNotes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    var isDelete:Boolean=false
)
