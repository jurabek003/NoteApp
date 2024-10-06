@file:OptIn(ExperimentalLayoutApi::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ModalBottomSheetUI(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel= hiltViewModel()
) {
    var textName by rememberSaveable {
        mutableStateOf("")
    }
    var textDescription by rememberSaveable {
        mutableStateOf("")
    }
    val scroll= rememberScrollState()
    val context = LocalContext.current
    var categoryId by rememberSaveable {
        mutableIntStateOf(1)
    }
    val categoryList=ArrayList<MyCategory>()
    categoryList.addAll(categoryViewModel.myCategories.value)
    Column(
        modifier = modifier
            .verticalScroll(scroll)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Note",
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = textName,
            onValueChange = {
                textName = it
            },
            label = {
                Text(text = "Name")
            },
            maxLines = 2,
            shape = ShapeDefaults.Medium,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = textDescription,
            onValueChange = {
                textDescription = it
            },
            label = {
                Text(text = "Description")
            },
            shape = ShapeDefaults.Medium,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = modifier
                .padding(horizontal = 12.dp)
                .heightIn(min = 150.dp, max = 300.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        FlowRow(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(categoryList.size) { index ->
                OutlinedCard(
                    onClick = {
                        categoryId = categoryList[index].id

                    },
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = if (categoryList[index].id == categoryId)
                            Color.Green
                        else
                            Color.Gray
                    ),
                    modifier=modifier
                ) {
                    Text(
                        text = categoryViewModel.myCategories.value[index].name,
                        fontWeight = FontWeight.Medium,
                        modifier=modifier
                        .padding(5.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
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
                    onDismiss()
                },
            ) {
                Text(text = "Close")
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    if (textName.isNotEmpty() && textDescription.isNotEmpty()) {
                        viewModel.insertNote(
                            Note(
                                name = textName, description = textDescription, categoryId = categoryId
                            )
                        )
                        onDismiss()
                    }else{
                        Toast.makeText(context, "Name and Description is empty", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Text(text = "Sava")
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}