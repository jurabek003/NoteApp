package uz.turgunboyevjurabek.noteapp.feature.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectedNoteScreen(
    navHostController: NavHostController
) {
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
                    Text(
                        text = "Assalom alekum",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }

    ){innerPadding->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(text = "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:" +
                    "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:" +
                    "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:" +
                    "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:" +
                    "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:" +
                    "Xabarda sizga berilayotgan xato shundan dalolat beradi: ilovangiz content:// URI orqali tanlangan faylga kirish uchun ruxsatga ega emas. Bu muammo Android 13 (API 33) va undan yuqori versiyalarda sodir bo'lishi mumkin, chunki yangi Android versiyalari ruxsatlarga qat'iyroq cheklovlar qo'ygan. Quyida buni qanday hal qilish mumkinligi haqida bir necha yo'l mavjud:",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(PaddingValues(12.dp))
            )

        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectedNoteScreenPreview() {
    val rememberNavController = rememberNavController()
    SelectedNoteScreen(navHostController =rememberNavController)
}