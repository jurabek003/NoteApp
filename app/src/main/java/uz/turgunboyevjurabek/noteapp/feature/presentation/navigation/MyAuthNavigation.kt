package uz.turgunboyevjurabek.noteapp.feature.presentation.navigation

import UserViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.AuthScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.MainScreen

@Composable
fun MyAuthNavigation(viewModel: UserViewModel,navHostController: NavHostController) {


    NavHost(navController = navHostController, startDestination = "AuthScreen"){
        composable("AuthScreen"){
            AuthScreen(viewModel = viewModel, navHostController = navHostController)
        }
    }

}