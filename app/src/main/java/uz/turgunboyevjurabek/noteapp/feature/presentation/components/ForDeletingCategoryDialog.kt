@file:OptIn(ExperimentalLayoutApi::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForDeletingCategoryDialog(
    modifier: Modifier = Modifier,
    onDismiss:() -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var categoryId by rememberSaveable {
        mutableIntStateOf(categoryViewModel.categoryById.value?.id ?: 0) // agar id null bulsa 0 ni bereadi
    }
    val selectCategory=categoryViewModel.categoryById.collectAsState()

    Dialog(
        onDismissRequest = {onDismiss()}
    ) {
        Card{
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
                        text = "${selectCategory.value?.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = modifier
                    )
                }
                Text(
                    text = "Ushbu Kategoriyani o'chirib tashlashingiz yoki tahrirlashingiz mumkin.",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier=modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    OutlinedIconButton(
                        onClick = {  },
                        modifier = modifier

                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }

                    OutlinedIconButton(
                        onClick = {  },
                        modifier = modifier

                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }


            }

        }
    }


}