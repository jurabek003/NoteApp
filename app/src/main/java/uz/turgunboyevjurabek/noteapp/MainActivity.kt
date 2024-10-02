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
                val viewModel = userViewModel.userState.collectAsState()

                val isEditNoteViewModel = IsEditNoteViewModel()

                // NavController ni yaratish
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                LaunchedEffect(viewModel.value) {
                    scope.launch {
                        startDestination =
                            viewModel.value?.name != null && viewModel.value?.image != null
                    }
                }
                // NavHost yordamida navigatsiya
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(
                            isLoggedIn = startDestination,
                            onNavigateToMain = {
                                navController.navigate("main") {
                                    // Splash ekranidan keyin navigatsiya qilishda oldingi ekranlarni olib tashlaydi
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            onNavigateToAuth = {
                                navController.navigate("auth") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            userViewModel = userViewModel
                        )
                    }
                    composable("main") {
                        MainScreen(
                            _userViewModel = userViewModel,
                            navHostController = navController
                        )
                    }
                    composable("auth") {
                        AuthScreen(
                            viewModel = userViewModel,
                            navHostController = navController
                        )
                    }
                    composable("selectedNote") {
                        SelectedNoteScreen(navController, isEditNoteViewModel = isEditNoteViewModel)
                    }
                    composable("isDelete") {
                        IsDeleteNotesScreen(navHostController = navController)
                    }
                    composable("profile"){
                        ProfileScreen(navHostController = navController, userViewModel = userViewModel)
                    }
                }
            }
        }

    }
}


