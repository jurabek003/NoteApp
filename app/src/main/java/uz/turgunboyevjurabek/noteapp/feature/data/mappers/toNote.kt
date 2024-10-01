package uz.turgunboyevjurabek.noteapp.feature.data.mappers

import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.NoteEntity
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        name = this.name,
        description = this.description,
        isDelete=this.isDelete
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        isDelete=this.isDelete
    )
}
