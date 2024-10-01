package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uz.turgunboyevjurabek.noteapp.core.utils.NoteObj
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim.myEnterTransition
import uz.turgunboyevjurabek.noteapp.feature.presentation.components.navigation_anim.myExitTransition
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.IsEditNoteViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun SelectedNoteScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel(),
    isEditNoteViewModel: IsEditNoteViewModel
) {
    val isEditView = isEditNoteViewModel.isEditNote.collectAsState()
    var isEdit by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(isEditView.value) {
        isEdit = isEditView.value
    }
    val getNoteById=NoteObj.noteID?.let { viewModel.getNoteById(it) }
    val note by viewModel.note.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier
                    .shadow(
                        elevation = 15.dp,
                        shape = MaterialTheme.shapes.extraLarge,
                        spotColor = Color.Yellow,
                        ambientColor = Color.Yellow,
                    )
                    .clip(MaterialTheme.shapes.extraLarge),
                title = {
                    AnimatedVisibility(
                        visible = !isEdit,
                        enter = slideInVertically(
                            animationSpec = tween(1000),
                            initialOffsetY = { _ -> 100f.toInt() }),
                        exit = slideOutVertically(
                            animationSpec = tween(1000),
                            targetOffsetY = { _ -> 100f.toInt() })
                    ) {
                        Text(
                            text = note?.name.toString(),
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Black
                        )
                    }
                    AnimatedVisibility(
                        visible = isEdit,
                        enter = slideInVertically(
                            animationSpec = tween(1000),
                            initialOffsetY = { _ -> 100f.toInt() }),
                        exit = slideOutVertically(
                            animationSpec = tween(1000),
                            targetOffsetY = { _ -> 100f.toInt() })
                    ) {
                        Text(
                            text = "Tahrirlash",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    /**
                     * Done Note
                     */
                    AnimatedVisibility(
                        visible = isEdit,
                        enter = slideInVertically(
                            animationSpec = tween(1000),
                            initialOffsetY = { _ -> 100f.toInt() }),
                        exit = slideOutVertically(
                            animationSpec = tween(1000),
                            targetOffsetY = { _ -> 100f.toInt() })
                    ){
                        IconButton(onClick = {
                            isEditNoteViewModel.setIsEditNote(false)
                        }) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = null)
                        }
                    }

                    /**
                     * Edit Note
                     */
                    AnimatedVisibility(
                        visible = !isEdit,
                        enter = fadeIn(tween(1500))
                    ) {
                        IconButton(onClick = {
                            isEditNoteViewModel.setIsEditNote(true)
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                    }

                    /**
                     * Delete Note
                     */
                    AnimatedVisibility(
                        visible = !isEdit,
                        enter = slideInVertically(
                            animationSpec = tween(1000),
                            initialOffsetY = { _ -> 100f.toInt() }),
                        exit = slideOutVertically(
                            animationSpec = tween(1000),
                            targetOffsetY = { _ -> 100f.toInt() })
                    ){
                        IconButton(onClick = {
                            note?.let { viewModel.updateNote(note = Note(id = it.id, name = it.name, description = it.description, isDelete = true) ) }
                            navHostController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = !isEdit,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                note?.let { DefaultNoteScreen(note = it) }
            }
            AnimatedVisibility(
                visible = isEdit,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                note?.let { EditNoteScreen(note = it, isEditNoteViewModel = isEditNoteViewModel) }
            }
        }

    }
}

@Composable
fun EditNoteScreen(
    note: Note,
    noteViewModel: NoteViewModel = hiltViewModel(),
    isEditNoteViewModel: IsEditNoteViewModel
) {
    val isEditView = isEditNoteViewModel.isEditNote.collectAsState()

    var name by rememberSaveable {
        mutableStateOf(note.name)
    }
    var description by rememberSaveable {
        mutableStateOf(note.description)
    }

    LaunchedEffect(isEditView.value) {
        when (isEditView.value) {
            false -> {
                NoteObj.apply {
                    noteID=note.id
                    noteName=name
                    noteDescription=description
                }
                noteViewModel.updateNote(Note(note.id, name = name, description = description))
            }
            else -> {
                Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Note Name : ",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 10.dp)
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            cursorBrush = Brush.linearGradient(listOf(Color.Cyan, Color.Red, Color.Yellow)),
            value = name,
            onValueChange = {
                name = it
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Black,
            ),
        )
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Note Description : ",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 10.dp)
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            cursorBrush = Brush.linearGradient(listOf(Color.Cyan, Color.Red, Color.Yellow)),
            value = description,
            onValueChange = {
                description = it
            },
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Black,
            ),
        )

    }

}


@Composable
fun DefaultNoteScreen(modifier: Modifier = Modifier, note: Note) {
    Text(
        text = note.description,
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier
            .padding(PaddingValues(12.dp))
    )

}
