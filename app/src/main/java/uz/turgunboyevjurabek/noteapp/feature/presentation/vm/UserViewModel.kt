import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.User
import javax.inject.Inject

class UserViewModel(
    private val dataStoreUser: DataStore<Preferences>
) : ViewModel() {

    private val USER_KEY = stringPreferencesKey("user_key")
    private val gson = Gson()

    // Holatni boshqarish uchun StateFlow
    private val _userState = MutableStateFlow<User?>(null) // Null holati
    val userState: StateFlow<User?> = _userState.asStateFlow()

    init {
        loadUser()
    }

    /**
     * Loads the User object from DataStore and updates the state.
     */
    private fun loadUser() {
        viewModelScope.launch {
            dataStoreUser.data.collect { preferences ->
                val userJson = preferences[USER_KEY] ?: return@collect
                val savedUser = gson.fromJson(userJson, User::class.java)
                _userState.value = savedUser ?: User(null, null) // Xatolik holati
            }
        }
    }

    /**
     * Retrieves the User object from StateFlow.
     */
    suspend fun getUser(): User? {
        return dataStoreUser.data.map { preferences ->
            val userJson = preferences[USER_KEY] ?: return@map null
            gson.fromJson(userJson, User::class.java)
        }.firstOrNull() // Birinchi qiymatni olish
    }

    /**
     * Saves the User object (name and image) to DataStore.
     */
    fun saveUser(newUser: User) {
        viewModelScope.launch {
            val userJson = gson.toJson(newUser)
            dataStoreUser.edit { mutablePreferences ->
                mutablePreferences[USER_KEY] = userJson
            }
            _userState.value = newUser // Muvaffaqiyatli saqlanganda Success holati
        }
    }

    /**
     * Clears the User object from DataStore.
     */
    fun clearUser() {
        viewModelScope.launch {
            dataStoreUser.edit { mutablePreferences ->
                mutablePreferences.clear()
            }
            _userState.value = null // Foydalanuvchi tozalanganidan keyin null
        }
    }
}
