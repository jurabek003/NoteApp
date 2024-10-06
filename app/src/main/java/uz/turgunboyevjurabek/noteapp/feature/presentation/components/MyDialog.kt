package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel

@Composable
fun MyDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    categoryViewModel: CategoryViewModel= hiltViewModel()
) {
    var viewModel=categoryViewModel.myCategories.collectAsState()

    var categoryName by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Dialog(
        onDismissRequest = { onDismiss() },
        content =  {
            Card {
                Column(
                    modifier = modifier
                        .wrapContentSize()
                ) {
                    Spacer(modifier = modifier.height(20.dp))

                    TextField(
                        value = categoryName,
                        singleLine = true,
                        onValueChange = {
                            categoryName = it
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
                                onDismiss()
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
                                if (categoryName.isNotEmpty()){
                                    categoryViewModel.addCategory(MyCategory(name = categoryName.trim()))
                                    onDismiss()
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
        }
    )
}
