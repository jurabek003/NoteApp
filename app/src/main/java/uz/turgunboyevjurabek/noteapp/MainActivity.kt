package uz.turgunboyevjurabek.noteapp

import SplashScreen
import UserViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.core.MyApp
import uz.turgunboyevjurabek.noteapp.feature.presentation.navigation.MyNavigation
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.AuthScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.IsDeleteNotesScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.MainScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.ProfileScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.SelectedNoteScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.IsEditNoteViewModel
import uz.turgunboyevjurabek.noteapp.ui.theme.NoteAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                // DataStore va ViewModel ni olish
                val dataStore = (application as MyApp).userDataStore
                val userViewModel = UserViewModel(dataStore)
                MyNavigation(userViewModel = userViewModel, dataStore = dataStore)
            }
        }

    }
}


