package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import uz.turgunboyevjurabek.noteapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    Scaffold {
        /**
         * Tanlangan rasmning Uri'sini saqlash uchun state
         */
        var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

        /**
         * Rasm tanlash uchun launcher
         */
        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) {uri: Uri? ->
                selectedImageUri=uri
            }

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
                            .border(0.dp, Color.Blue,ShapeDefaults.ExtraLarge)
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    // Agar rasm tanlanmagan bo'lsa, default rasm ko'rsatish
                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
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
//                contentPadding = PaddingValues(horizontal = 20.dp),
                onClick = { }
            ) {
                Text(
                    text = "Saqlash",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }
        }
        /**
         *
         */
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {

    AuthScreen()

}