package uz.turgunboyevjurabek.noteapp.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.NoteDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "MyNotes"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()


}