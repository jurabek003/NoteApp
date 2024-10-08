@file:OptIn(ExperimentalMaterial3Api::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import UserViewModel
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape

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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.AutoResizingText
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.CustomTab
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.ForDeletingCategoryDialog
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.ModalBottomSheetUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.MyDialog
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.NoteListUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.IsEditCategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    myViewModel: NoteViewModel = hiltViewModel(),
    _userViewModel: UserViewModel,
    navHostController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current

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
    var dialogForCategoryDeleting by rememberSaveable {
        mutableStateOf(false)
    }

    val notes by myViewModel.notes.collectAsState()

    val myCategoryViewModel by categoryViewModel.myCategories.collectAsState()

    val mainSurfaceShadowColor = if (isSystemInDarkTheme()) Color.Green else Color.Red

    val userViewModel = _userViewModel.userState.collectAsState()
    val tabs = ArrayList<MyCategory>()
    tabs.addAll(myCategoryViewModel)

    val selectedCategory = myCategoryViewModel.getOrNull(selectedTabIndex)?.id
    val filteredNotes = notes.filter {
        it.categoryId == selectedCategory && !it.isDelete
    }
    val isEditCategoryViewModel= viewModel<IsEditCategoryViewModel>()
    val imageLoader = ImageLoader.Builder(context)
        .error(R.drawable.ic_launcher_foreground)
        .crossfade(500)
        .placeholder(R.drawable.ic_image)
        .memoryCachePolicy(CachePolicy.ENABLED) //Keshlash
        .build()

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
                            shadowElevation = 35f
                            shape = ShapeDefaults.ExtraLarge
                        },
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                    shape = ShapeDefaults.ExtraLarge,
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
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "Hello, ${userViewModel.value?.name}",
                                    style = TextStyle(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.Serif,
                                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .fillMaxWidth(0.8f),
                                )
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = userViewModel.value?.image,
                                        imageLoader = imageLoader
                                    ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .border(
                                                width = 1.dp,
                                                color = Color.LightGray,
                                                shape = CircleShape
                                            )
                                            .clickable {
                                                navHostController.navigate("profile")
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = {},
                    divider = {},
                    edgePadding = 0.dp,
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
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Black else FontWeight.Normal,
                                    fontSize = if (selectedTabIndex == index) 20.sp else 14.sp
                                )
                            },
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            categoryViewModel.getCategoryById(myCategory = myCategory)
                                            dialogForCategoryDeleting = true
                                        },
                                        onTap = {
                                            selectedTabIndex = index
                                        }
                                    )
                                }
                        ) {}
                    }
                    Surface(
                        modifier = Modifier
                            .wrapContentSize()
                            .graphicsLayer {
                                spotShadowColor = Color.Yellow
                                shadowElevation = 18f
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
                                .size(35.dp)
                        )
                    }

                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = (-15).dp,
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    val myNotes =
                        if (selectedCategory == 1) notes.filter { !it.isDelete } else filteredNotes
                    items(myNotes.size) { note ->
                        AnimatedVisibility(
                            visible = true, // You can add conditions based on category change if needed
                            enter = fadeIn(animationSpec = tween(500)) + expandVertically(),
                            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically()
                        ) {
                            NoteListUI(
                                notes = myNotes[note],
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

            /**
             * CategoryInfo Dialog for Deleting
             */
            if (dialogForCategoryDeleting) {
                ForDeletingCategoryDialog(
                    onDismiss = { dialogForCategoryDeleting = false },
                    isEditCategoryViewModel=isEditCategoryViewModel
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
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
                FloatingActionButton(
                    modifier = modifier
                        .padding(bottom = 50.dp)
                        .graphicsLayer {
                            spotShadowColor = Color.Green
                            shadowElevation = 7f
                            shape = CircleShape
                        },
                    onClick = {
                        isSheetOpen = true
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
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
                        tint = Color.White,
                        modifier = Modifier
                            .size(35.dp)
                    )
                }
            }
        }
    )
}