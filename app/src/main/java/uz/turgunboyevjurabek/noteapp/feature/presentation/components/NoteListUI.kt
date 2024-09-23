package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

@Composable
fun NoteListUI(modifier: Modifier = Modifier,notes :Note) {
    Surface(
        modifier=modifier
            .statusBarsPadding()
            .wrapContentSize(),
        shadowElevation = 3.dp,
        shape = ShapeDefaults.ExtraLarge
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = notes.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            )
            Text(
                text = notes.description,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    
}
@Preview
@Composable
fun NoteListUIPreview(){
    val note=Note(0,"Mening birinchi noteim","Assalomu alaykum Bu mening birinchi notim")
    NoteListUI(notes = note)
}
        