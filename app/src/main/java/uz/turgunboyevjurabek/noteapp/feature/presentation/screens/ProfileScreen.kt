package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.core.MyApp

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {
    val userState=userViewModel.userState.collectAsState()

    val user=userViewModel.userState.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (profileImage, surface,navigationIcon,editIcon) = createRefs()
        val imageLoading = ImageLoader.Builder(LocalContext.current)
            .crossfade(1000)
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_image)
            .build()
        Image(
            painter = rememberAsyncImagePainter(
                model = user?.image,
                imageLoader = imageLoading
            ),

            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.4f)
                .constrainAs(profileImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        IconButton(
            onClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier
                .size(40.dp)
                .constrainAs(navigationIcon) {
                    top.linkTo(parent.top, margin = 30.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Cyan,
                modifier = Modifier.size(25.dp)
            )
        }
        IconButton(
            onClick = {  },
            modifier = Modifier
                .size(40.dp)
                .constrainAs(editIcon) {
                    top.linkTo(parent.top, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.Cyan,
                modifier = Modifier.size(25.dp)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
//
                .constrainAs(surface) {
                    top.linkTo(profileImage.bottom, margin = (-40).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(
                topEnd = 40.dp,
                topStart = 40.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = user?.name.toString(),
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                }
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    val navHostController = rememberNavController()

//    ProfileScreen(navHostController = navHostController)
}