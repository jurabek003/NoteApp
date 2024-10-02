@file:OptIn(ExperimentalMaterial3Api::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.core.MyApp
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.User
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim.myEnterTransition
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.IsEditProfileViewModel
import uz.turgunboyevjurabek.noteapp.ui.theme.NoteAppTheme

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel,
    isEditProfileViewModel: IsEditProfileViewModel = viewModel<IsEditProfileViewModel>()
) {
    val scope= rememberCoroutineScope()

    val shadowColor = if (isSystemInDarkTheme()) Color.Green else Color.Red
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val editProfile = isEditProfileViewModel.editProfile.collectAsState()
    var isEdit by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(editProfile.value) {
        isEdit = editProfile.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = ShapeDefaults.ExtraLarge,
                        spotColor = shadowColor
                    ),
                scrollBehavior = scrollBehavior,
                title = {
                    AnimatedVisibility(
                        visible = !isEdit,
                        enter = slideInVertically(
                            tween(700),
                            initialOffsetY = { _ -> 200}
                        ),
                        exit = slideOutVertically(
                            tween(600),
                            targetOffsetY = { _ -> 200}
                        ),
                        content = {
                            Text(
                                text = "ProfileScreen",
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    )
                    AnimatedVisibility(
                        visible = isEdit,
                        enter = slideInVertically(
                            tween(700),
                            initialOffsetY = { _ -> 100}
                        ),
                        exit = slideOutVertically(
                            tween(600),
                            targetOffsetY = { _ -> 100}
                        ),
                        content = {
                            Text(
                                text = "EditProfileScreen",
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    )

                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = !isEdit,
                        enter = slideInVertically(),
                        exit = slideOutVertically(),
                        content = {
                            IconButton(onClick = {
                                isEditProfileViewModel.setIsEditProfile(true)
                            }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                            }
                        }
                    )
                    AnimatedVisibility(
                        visible = isEdit,
                        enter = slideInVertically(),
                        exit = slideOutVertically(),
                        content = {
                            IconButton(onClick = {
                                isEditProfileViewModel.setIsEditProfile(false)
                            }) {
                                Icon(imageVector = Icons.Default.Done, contentDescription = null)
                            }
                        }
                    )

                }
            )
        },
        content ={
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .verticalScroll(state = scrollState),
            ) {
                AnimatedVisibility(visible = !isEdit) {
                    DefaultProfileScreen(
                        userViewModel = userViewModel,
                        navHostController = navHostController,
                        isEditProfileViewModel = isEditProfileViewModel
                    )
                }
                AnimatedVisibility(visible = isEdit) {
                    EditProfileScreen(
                        user = User(userViewModel.userState.value?.name,userViewModel.userState.value?.image),
                        isEditProfileViewModel = isEditProfileViewModel,
                        userViewModel = userViewModel
                    )
                }
            }
        }
    )

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DefaultProfileScreen(
    userViewModel: UserViewModel,
    navHostController: NavHostController,
    isEditProfileViewModel: IsEditProfileViewModel
) {
    val userState = userViewModel.userState.collectAsState()

    val user = userViewModel.userState.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (profileImage, surface, navigationIcon, editIcon) = createRefs()
        val imageLoading = ImageLoader.Builder(LocalContext.current)
            .crossfade(800)
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
                .constrainAs(profileImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(surface) {
                    top.linkTo(profileImage.bottom, margin = (-40).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(
                topEnd = 30.dp,
                topStart = 30.dp
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
fun EditProfileScreen(
    user:User,
    isEditProfileViewModel: IsEditProfileViewModel,
    userViewModel: UserViewModel
) {

    val isEditProfile = isEditProfileViewModel.editProfile.collectAsState()

    var changeImage by rememberSaveable {
        mutableStateOf(user.image)
    }
    var changeName by rememberSaveable {
        mutableStateOf(user.name)
    }

    val context=LocalContext.current

    LaunchedEffect(isEditProfile.value) {
        when(isEditProfile.value){
            false -> {
                userViewModel.saveUser(newUser = User(name = changeName, image = changeImage))
                Toast.makeText(context, "saqlandi", Toast.LENGTH_SHORT).show()
            }
            else ->{
                Unit
            }
        }
    }

    val imageLoading = ImageLoader.Builder(LocalContext.current)
        .crossfade(400)
        .placeholder(R.drawable.ic_account)
        .error(R.drawable.ic_launcher_foreground)
        .build()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
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