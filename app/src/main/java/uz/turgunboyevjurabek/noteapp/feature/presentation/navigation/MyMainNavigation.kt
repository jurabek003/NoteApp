package uz.turgunboyevjurabek.noteapp.feature.presentation.navigation

import UserViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.MainScreen

@Composable
fun MyMainNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {
    NavHost(navController = navHostController, startDestination ="MainScreen" ){
        composable("MainScreen"){
            MainScreen(navHostController = navHostController, _userViewModel = userViewModel)
        }
    }

}