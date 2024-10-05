@file:OptIn(ExperimentalLayoutApi::class)

package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory
import uz.turgunboyevjurabek.noteapp.feature.presentation.vm.CategoryViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ForDeletingSheetUI(
    modifier: Modifier = Modifier,
//    categoryViewModel: CategoryViewModel= hiltViewModel()
) {
    val context = LocalContext.current
    var categoryId by rememberSaveable {
        mutableIntStateOf(1)
    }
    val categoryList = ArrayList<MyCategory>()
//    categoryList.addAll(categoryViewModel.categories.value)

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        FlowRow(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(30) { index ->
                ElevatedCard(
                    onClick = {},
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = Color.Gray,
                    ),
                    modifier = modifier
                        .wrapContentSize(unbounded = true),
                ) {
                    Column(
                        modifier = modifier
                            .padding(7.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "My Category", modifier = modifier)
                        Text(text = "note(12)", modifier = modifier)
                    }
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun ForDeletingSheetUIPreview() {
    ForDeletingSheetUI()
}