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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.User
import uz.turgunboyevjurabek.noteapp.feature.presentation.state.ResultState
import javax.inject.Inject

class UserViewModel(
    private val dataStoreUser: DataStore<Preferences>
) : ViewModel() {

    private val USER_KEY = stringPreferencesKey("user_key")
    private val gson = Gson()

    // Holatni boshqarish uchun StateFlow
    private val _userState = MutableStateFlow(User(null, null))
    val userState: StateFlow<User> = _userState.asStateFlow()

    init {
        // Load user data on initialization
        viewModelScope.launch {
            loadUser()
        }
    }

    /**
     * Loads the User object from DataStore and updates the state.
     */
    private suspend fun loadUser() {
        dataStoreUser.data.map { preferences ->
            val userJson = preferences[USER_KEY] ?: ""
            gson.fromJson(userJson, User::class.java)
        }.collect { savedUser ->
            if (savedUser!=null){
                _userState.value = savedUser // Success holati
            }
        }
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

    fun clearUser() {
        viewModelScope.launch {
            dataStoreUser.edit { mutablePreferences ->
                mutablePreferences.clear()
                _userState.value = User(null, null)
            }
        }
    }
}
