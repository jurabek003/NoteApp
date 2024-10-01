package uz.turgunboyevjurabek.noteapp.feature.domein.madels

import androidx.room.Delete
import androidx.room.Entity
import java.text.DateFormat
import java.text.SimpleDateFormat


data class Note(
    val id:Int=0,
    val name:String,
    val description:String,
    var isDelete: Boolean=false
)
