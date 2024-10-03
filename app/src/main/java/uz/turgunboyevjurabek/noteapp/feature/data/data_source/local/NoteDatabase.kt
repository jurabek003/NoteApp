package uz.turgunboyevjurabek.noteapp.feature.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class, MyCategoryEntity::class], version = 5 )
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao // Yangi category DAO

}
