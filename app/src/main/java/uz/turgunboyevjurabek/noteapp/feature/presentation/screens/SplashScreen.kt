import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uz.turgunboyevjurabek.noteapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    isLoggedIn: Boolean,
    onNavigateToMain: () -> Unit,
    onNavigateToAuth: () -> Unit,
    userViewModel: UserViewModel
) {
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
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSystemInDarkTheme())
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.tertiaryContainer
                    ),
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
    )


}
