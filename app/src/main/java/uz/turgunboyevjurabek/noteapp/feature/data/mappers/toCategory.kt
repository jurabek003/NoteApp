package uz.turgunboyevjurabek.noteapp.feature.data.mappers

import uz.turgunboyevjurabek.noteapp.feature.data.data_source.local.MyCategoryEntity
import uz.turgunboyevjurabek.noteapp.feature.domein.madels.MyCategory

fun MyCategoryEntity.toCategory():MyCategory{
    return MyCategory(
        id = this.id,
        name = this.name
    )

}