@file:OptIn(ExperimentalLayoutApi::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.DefaultNoteScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.screens.EditNoteScreen
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.IsEditCategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel
@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForDeletingCategoryDialog(
    onDismiss: () -> Unit,
    isEditCategoryViewModel: IsEditCategoryViewModel
) {
    val isEditCategory = isEditCategoryViewModel.isEditCategory.collectAsState()

    var isEdit by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(isEditCategory.value) {
        isEdit=isEditCategory.value
    }

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card {
            AnimatedVisibility(
                visible = isEdit,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                EditCategoryDialogScreen(
                    isEditCategoryViewModel = isEditCategoryViewModel
                )
            }
            AnimatedVisibility(
                visible = !isEdit,
                enter = fadeIn(animationSpec = tween(1000)),
                exit = fadeOut(animationSpec = tween(800))
            ) {
                DefaultDialogScreen(
                    onDismiss = { onDismiss() },
                    isEditCategoryViewModel = isEditCategoryViewModel
                )
            }

        }
    }
}

@Composable
fun EditCategoryDialogScreen(
    modifier: Modifier = Modifier,
    isEditCategoryViewModel: IsEditCategoryViewModel,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {
    val isEditCategory by isEditCategoryViewModel.isEditCategory.collectAsState()

    val context = LocalContext.current
    val selectCategory by categoryViewModel.categoryById.collectAsState()

    var newCategoryName by rememberSaveable {
        mutableStateOf(selectCategory?.name)
    }

    Column(
        modifier = modifier
            .wrapContentSize()
    ) {
        Spacer(modifier = modifier.height(20.dp))
        TextField(
            value = newCategoryName.toString(),
            singleLine = true,
            onValueChange = {
                newCategoryName = it
            },
            placeholder = {
                Text(text = "Category name",Modifier.alpha(0.7f))
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = modifier.height(20.dp))
        Row(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    isEditCategoryViewModel.setIsEditCategory(false)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                )
                Text(text = "Close")
            }
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    if (newCategoryName?.isNotEmpty() == true){
                        categoryViewModel.updateCategory(MyCategory(id = selectCategory!!.id, name = newCategoryName!!.trim()))
                        isEditCategoryViewModel.setIsEditCategory(false)
                    }else {
                        Toast.makeText(context, "Name is empty", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier
                )
                Text(text = "Sava")
            }
        }
        Spacer(modifier = modifier.height(20.dp))
    }

}
@Composable
fun DefaultDialogScreen(
    modifier: Modifier = Modifier,
    isEditCategoryViewModel: IsEditCategoryViewModel,
    onDismiss: () -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    noteViewModel: NoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectCategory by categoryViewModel.categoryById.collectAsState()
    val myNoteViewModel by noteViewModel.notes.collectAsState(initial = emptyList())
    val notesByCategory by noteViewModel.getNotesByCategoryId.collectAsState(initial = emptyList())

    var categoryId by rememberSaveable {
        mutableIntStateOf(selectCategory?.id ?: 0)
    }
    var newCategoryName by rememberSaveable {
        mutableStateOf(selectCategory?.name)
    }

    noteViewModel.loadNotesByCategoryId(categoryId,false)

    val allNotes = myNoteViewModel.filter { !it.isDelete }

    LaunchedEffect(selectCategory) {
        categoryId=selectCategory?.id?:0
        noteViewModel.loadNotesByCategoryId(categoryId,false)
        newCategoryName=selectCategory?.name
    }

    Column(
        modifier = modifier
            .padding(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { onDismiss() },
                modifier = modifier
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "close icon")
            }
            Text(
                text = "${selectCategory?.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
        }
        Text(
            text = if ( notesByCategory.isNotEmpty() && categoryId != 1)
                "This category contains ${notesByCategory.size} notes"
            else if (categoryId == 1)
                "All notes in this category are ${allNotes.size}"
            else
                "This category is empty",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = modifier
                .graphicsLayer {
                    shape = ShapeDefaults.ExtraLarge
                    shadowElevation = 1f
                    spotShadowColor = Color.Green
                }
                .fillMaxWidth()
        )
        Spacer(modifier = modifier.height(20.dp))
        if (notesByCategory.isNotEmpty()) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null, modifier = modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = "if you delete this category all notes will be deleted",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                modifier = modifier
                    .border(
                        1.dp,
                        brush = Brush.linearGradient(listOf(Color.Green, Color.Red)),
                        shape = ShapeDefaults.Medium
                    )
                    .fillMaxWidth()
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            if (categoryId != 1){
                OutlinedIconButton(
                    onClick = {
                        isEditCategoryViewModel.setIsEditCategory(true)
                    },
                    modifier = modifier
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                OutlinedIconButton(
                    onClick = {
                        categoryViewModel.deleteCategory(selectCategory!!)
                        noteViewModel.deleteNoteByCategoryId(categoryId)
                        onDismiss()
                    },
                    modifier = modifier
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }

}
