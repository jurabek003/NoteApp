package uz.turgunboyevjurabek.noteapp.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.CategoryDao
import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.NoteDao
import uz.turgunboyevjurabek.noteapp.feature.data.repository_impl.CategoryRepositoryImpl
import uz.turgunboyevjurabek.noteapp.feature.data.repository_impl.NoteRepositoryImpl
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.CategoryRepository
import uz.turgunboyevjurabek.noteapp.feature.domein.repository.NoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(dao)
    }
    @Provides
    @Singleton
    fun provideCategoryRepository(dao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(dao)
    }
}
