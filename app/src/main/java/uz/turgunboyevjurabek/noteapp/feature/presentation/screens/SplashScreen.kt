import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uz.turgunboyevjurabek.noteapp.R

@Composable
fun SplashScreen(
    isLoggedIn: Boolean,
    onNavigateToMain: () -> Unit,
    onNavigateToAuth: () -> Unit,
    userViewModel: UserViewModel
) {
//    if (isLoggedIn){
//        onNavigateToMain()
//    }else{
//        onNavigateToAuth()
//    }

    val user by userViewModel.userState.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            delay(600) // Splash screen ko'rsatish uchun biroz kechikish
            onNavigateToMain()
        } else {
            delay(1000) // Splash screen ko'rsatish uchun biroz kechikish
            onNavigateToAuth()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
        )

    }
}
