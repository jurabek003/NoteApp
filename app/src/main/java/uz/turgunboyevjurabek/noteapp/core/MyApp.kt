package uz.turgunboyevjurabek.noteapp.core

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application() {
    val userDataStore: DataStore<Preferences> by preferencesDataStore(name="User")
}