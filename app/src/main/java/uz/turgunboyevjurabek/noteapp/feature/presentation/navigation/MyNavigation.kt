package uz.turgunboyevjurabek.noteapp.feature.presentation.navigation

import UserViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.AuthScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.MainScreen

@Composable
fun MyNavigation(viewModel: UserViewModel,navHostController: NavHostController) {
    val userViewModel=viewModel.userState.collectAsState()
    val startDestination:String=if (userViewModel.value.name != null && userViewModel.value.image != null) "MainScreen" else "AuthScreen"
    NavHost(navController = navHostController, startDestination = startDestination){
        composable("AuthScreen"){
            AuthScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable("MainScreen"){
            MainScreen(navHostController = navHostController)
        }
    }

}