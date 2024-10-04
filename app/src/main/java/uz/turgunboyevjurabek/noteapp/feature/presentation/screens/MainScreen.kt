@file:OptIn(ExperimentalMaterial3Api::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.util.CoilUtils
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.core.utils.NoteObj
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.CustomTab
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.ModalBottomSheetUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.MyDialog
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.NoteListUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel(),
    _userViewModel: UserViewModel,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState()
    val scope = rememberCoroutineScope()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val myCategoryViewModel=categoryViewModel.categories.collectAsState()

    val mainSurfaceShadowColor = if (isSystemInDarkTheme()) Color.Green else Color.Red

    val userViewModel = _userViewModel.userState.collectAsState()
    val tabs=ArrayList<MyCategory>()
    tabs.addAll(myCategoryViewModel.value)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            Column {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(unbounded = true)
                        .graphicsLayer {
                            spotShadowColor = mainSurfaceShadowColor
                            shadowElevation = 10f
                            shape = ShapeDefaults.ExtraLarge
                        }
                        .background(Color.Transparent),
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                    shape = ShapeDefaults.ExtraLarge,
                    shadowElevation = 8.dp,
                    content = {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 50.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomEnd = 30.dp,
                                            bottomStart = 30.dp
                                        )
                                    ),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Hello, ${userViewModel.value?.name}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = MaterialTheme.typography.displaySmall.fontSize
                                )
                                val imageLoader = ImageLoader.Builder(context)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .crossfade(500)
                                    .placeholder(R.drawable.ic_image)
                                    .memoryCachePolicy(CachePolicy.ENABLED) //Keshlash
                                    .build()
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = userViewModel.value?.image,
                                        imageLoader = imageLoader
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            navHostController.navigate("profile")
                                        }
                                        .border(
                                            border = BorderStroke(1.5.dp, color = Color.Red),
                                            shape = CircleShape
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = {},
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, myCategory ->
                        CustomTab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = myCategory.name,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = if(selectedTabIndex==index) 20.sp else 15.sp
                                )
                            }
                        ){}
                    }
                }
                /*
                 item {
                        Surface(
                            modifier = Modifier
                                .wrapContentSize()
                                .graphicsLayer {
                                    spotShadowColor = Color.Yellow
                                    shadowElevation = 30f
                                    shape = ShapeDefaults.Large
                                },
                            shape = ShapeDefaults.Medium,
                            onClick = {
                                isDialogOpen = !isDialogOpen
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                 */
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = (-15).dp,
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    items(notes.size) { note ->
                        NoteListUI(
                            notes = notes[note],
                            onClick = { notes ->
                                NoteObj.apply {
                                    noteID = notes.id
                                    noteName = notes.name
                                    noteDescription = notes.description
                                }
                                navHostController.navigate("selectedNote")
                            }
                        )
                    }
                }

            }
            /**
             * For BottomSheetDialog
             */
            val bottomSheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = {
                    it == SheetValue.Expanded
                }
            )
            if (isSheetOpen) {
                ModalBottomSheet(
                    tonalElevation = 15.dp,
                    sheetState = bottomSheetState,
                    onDismissRequest = { isSheetOpen = false },
                    properties = ModalBottomSheetProperties(
                        securePolicy = SecureFlagPolicy.SecureOn,
                        isFocusable = true,
                        shouldDismissOnBackPress = false,
                    ),
                ) {
                    ModalBottomSheetUI(
                        onDismiss = { isSheetOpen = false }
                    )
                }
            }

            /**
             * For Dialog
             */
            if (isDialogOpen) {
                MyDialog(
                    onDismiss = {
                        isDialogOpen = false
                    }
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 0.99f
                    }
                    .background(
                        color = Color.Transparent
                    )
                    .paint(
                        painter = painterResource(id = R.drawable.bottom_navigation),
                        contentScale = ContentScale.FillWidth
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = 50.dp),
                    onClick = {
                        isSheetOpen = true
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
//                                containerColor = Color.Transparent,
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        navHostController.navigate("isDelete")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
            }
        }
    )
}