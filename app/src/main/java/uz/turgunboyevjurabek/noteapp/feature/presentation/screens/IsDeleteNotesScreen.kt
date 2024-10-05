@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uz.turgunboyevjurabek.noteapp.R
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.NoteListUI
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@Composable
fun IsDeleteNotesScreen(
    noteViewModel: NoteViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current
    noteViewModel.getIsDeleteNotes(true)
    val isDeleteNotes by noteViewModel.isDeleteNote.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        15.dp,
                        spotColor = Color.Red,
                        ambientColor = Color.Red,
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .clip(MaterialTheme.shapes.extraLarge),
                title = {
                    Text(text = "Deleted Notes",fontWeight = FontWeight.Black, fontFamily = FontFamily.Serif)
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.popBackStack() },
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription =null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            Box(
//                ntainerColor = MaterialTheme.colorScheme.surface,
//                elevation = FloatingActionButtonDefaults.loweredElevation(10.dp),
                contentAlignment = Alignment.Center,
                modifier = Modifier
//                    .combinedClickable(
//                        enabled = true,
//                        onClick = {
//                            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
//                        },
//                        onLongClick = {
//                            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
//                        }
//                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                navHostController.popBackStack()
                            }
                        )
                    }
                    .size(60.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category_black),
                    contentDescription = null,
                    modifier = Modifier
                        .size(27.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)  // nestedScroll qo'shish
                    .fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalItemSpacing = (-15).dp
            ) {
                items(isDeleteNotes.size) {
                    isDeleteNotes[it]?.let { it1 ->
                        NoteListUI(notes = it1) {
                        }
                    }
                }
            }
        }

    }


}