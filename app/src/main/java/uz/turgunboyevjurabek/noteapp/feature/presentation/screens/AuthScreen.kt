package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.User
import uz.turgunboyevjurabek.noteapp.feature.presentation.state.ResultState
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navHostController: NavHostController
)  {
    val user by viewModel.userState.collectAsState()
    val context=LocalContext.current

    categoryViewModel.addCategory(MyCategory(1,"All"))

    Scaffold {
        /**
         * Tanlangan rasmning Uri'sini saqlash uchun state
         */
        var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

        /**
         * Rasm tanlash uchun launcher
         */
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let {
                    // Doimiy ruxsat olish
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    selectedImageUri=it
                }
            }
        )

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var name by rememberSaveable {
                mutableStateOf("")
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clickable {
                        // Rasm tanlash uchun galereyani ochish
                        launcher.launch("image/*")
                    }
                    .clip(ShapeDefaults.ExtraLarge)
                    .border(
                        2.dp, Brush.linearGradient(
                            listOf(
                                Color.Red,
                                Color.Blue,
                            )
                        ), shape = ShapeDefaults.ExtraLarge
                    ),

                ) {
                if (selectedImageUri != null) {
                    // Tanlangan rasmni ekranda ko'rsatish
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .border(0.dp, Color.Blue, ShapeDefaults.ExtraLarge)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
                else {
                    // Agar rasm tanlanmagan bo'lsa, default rasm ko'rsatish
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier
                            .border(0.dp, Color.Blue, ShapeDefaults.ExtraLarge)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,

                    )
                }
            }

            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                placeholder = {
                    Text(text = "Name")
                },
                shape = ShapeDefaults.ExtraLarge,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .border(
                        2.dp, Brush.linearGradient(
                            listOf(
                                Color.Red,
                                Color.Blue,
                            )
                        ), shape = ShapeDefaults.ExtraLarge
                    ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(100.dp))
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                onClick = {
                    if (name.isNotEmpty() && selectedImageUri.toString().isNotEmpty()){
                        Toast.makeText(context, "saqlandi", Toast.LENGTH_SHORT).show()
                        viewModel.saveUser(User(name,selectedImageUri.toString()))
                        navHostController.navigate("main"){
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
            ) {
                Text(
                    text = "Saqlash",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }


            /**
             * Status management
             */
//            when(user){
//               is ResultState.Loading ->{
//                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//                }
//                is ResultState.Success ->{
//                    Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_SHORT).show()
//                }
//                is ResultState.Error ->{
//                    Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
//                }
//                else-> Unit
//            }

        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {

//    AuthScreen()

}