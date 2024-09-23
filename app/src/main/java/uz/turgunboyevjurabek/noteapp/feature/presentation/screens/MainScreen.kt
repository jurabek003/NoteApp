@file:OptIn(ExperimentalMaterial3Api::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.ModalBottomSheetUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.NoteListUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                        isSheetOpen=true
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
//                                containerColor = Color.Transparent,
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                    )

                }
            }
        },
        content = {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = (-15).dp,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(notes.size) { note ->
                    NoteListUI(notes = notes[note])
                }
            }

            /**
             * For BottomSheetDialog
             */
            val bottomSheetState = rememberModalBottomSheetState()
            if (isSheetOpen){
                ModalBottomSheet(
                    sheetState = bottomSheetState,
                    onDismissRequest = { isSheetOpen=false }
                ) {
                    ModalBottomSheetUI(
                        onDismiss = { isSheetOpen = false }
                    )
                }
            }
        }
    )

}