@file:OptIn(ExperimentalMaterial3Api::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.core.MyApp
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.User
import uz.turgunboyevjurabek.noteapp.ui.theme.NoteAppTheme

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {
    val shadowColor = if (isSystemInDarkTheme()) Color.Green else Color.Red
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = ShapeDefaults.ExtraLarge,
                        spotColor = shadowColor
                    ),
                scrollBehavior = scrollBehavior,
                title = {
                Text(
                    text = "ProfileScreen",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
    ){innerPadding->
        val scrollState= rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center
        ) {
            EditProfileScreen(userViewModel = userViewModel)
        }
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultProfileScreen(
    userViewModel: UserViewModel,
    navHostController: NavHostController
) {

    val userState = userViewModel.userState.collectAsState()

    val user = userViewModel.userState.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (profileImage, surface, navigationIcon, editIcon) = createRefs()
        val imageLoading = ImageLoader.Builder(LocalContext.current)
            .crossfade(1000)
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_launcher_foreground)
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
            onClick = { },
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

@Composable
fun EditProfileScreen(userViewModel: UserViewModel) {
    val user = userViewModel.userState.collectAsState()

    val imageLoading = ImageLoader.Builder(LocalContext.current)
        .crossfade(400)
        .placeholder(R.drawable.ic_account)
        .error(R.drawable.ic_launcher_foreground)
        .build()

    val changeImage by rememberSaveable {
        mutableStateOf(user.value?.image)
    }
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val shadowColor = if (isSystemInDarkTheme()) Color.Green else Color.Red
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .graphicsLayer {
                    spotShadowColor = shadowColor
                    shadowElevation = 30f
                    shape = RoundedCornerShape(50.dp)
                },
            shape = RoundedCornerShape(50.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                var changeName by remember {
                    mutableStateOf(user.value?.name)
                }
                val myBrush = Brush.linearGradient(
                    listOf(
                        Color.Red,
                        Color.Blue
                    )
                )
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .graphicsLayer {
                            spotShadowColor = shadowColor
                            shadowElevation = 50f
                            shape = RoundedCornerShape(110.dp)
                        }
                        .clip(RoundedCornerShape(90.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = changeImage,
                            imageLoader = imageLoading
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { }
                            .clip(ShapeDefaults.ExtraLarge)
                            .border(3.dp, brush = myBrush, shape = RoundedCornerShape(90.dp))
                            .fillMaxSize()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                spotShadowColor = Color.Green
                                shadowElevation = 3f
                                shape = ShapeDefaults.ExtraLarge
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    value = changeName!!,
                    onValueChange = {
                        changeName = it
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    shape = ShapeDefaults.ExtraLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedBorderColor = Color.DarkGray,
                        focusedBorderColor = Color.LightGray,
                        unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    ),
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }

    }

}


@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    val navHostController = rememberNavController()
//    val userViewModel= viewModel<UserViewModel>()
//    EditProfileScreen(userViewModel)

}