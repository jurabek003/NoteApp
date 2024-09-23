package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.NoteViewModel

@Composable
fun ModalBottomSheetUI(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel(),
//    content: @Composable () -> Unit
) {
    var textName by rememberSaveable {
        mutableStateOf("")
    }
    var textDescription by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textName,
            onValueChange = {
                textName = it
            },
            label = {
                Text(text = "Name")
            },
            shape = ShapeDefaults.ExtraLarge,
            modifier = modifier
                .padding(5.dp)
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
            shape = ShapeDefaults.ExtraLarge,
            modifier = modifier
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedIconButton(
            modifier = modifier
                .size(50.dp),
            onClick = {
                if (textName.isNotEmpty() && textDescription.isNotEmpty()) {
                    viewModel.insertNote(
                        Note(
                            name = textName, description = textDescription
                        )
                    )
                    onDismiss()
                }else{
                    Toast.makeText(context, "Name and Description is empty", Toast.LENGTH_SHORT).show()
                }
            },
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
//            Text(text = "Add", fontWeight = FontWeight.ExtraBold)
        }
    }
}