package uz.turgunboyevjurabek.noteapp.feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

@Composable
fun NoteListUI(modifier: Modifier = Modifier,notes:Note,onClick:(Note)->Unit) {
    Surface(
        onClick = {
            onClick(notes)
        },
        modifier= modifier
            .statusBarsPadding()
            .wrapContentSize()
            .padding( PaddingValues(horizontal = 8.dp)),
        shadowElevation = 5.dp,
        shape = ShapeDefaults.ExtraLarge
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(PaddingValues(10.dp))
        ) {
            val (name,description)=createRefs()
            Text(
                text = notes.name,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.ExtraBold),
                modifier = modifier
                    .constrainAs(name){
                        start.linkTo(parent.start, margin = 7.dp)
                        top.linkTo(parent.top, margin = 7.dp)
                    }
            )
            Text(
                text = notes.description,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Light),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .constrainAs(description){
                        start.linkTo(parent.start, margin = 7.dp)
                        top.linkTo(name.bottom, margin = 7.dp)
                    }
            )
        }
    }
    
}
@Preview
@Composable
fun NoteListUIPreview(){
    val note=Note(0,"Mening birinchi noteim","Assalomu alaykum Bu mening birinchi notim")
//    NoteListUI(notes = note)
}
        