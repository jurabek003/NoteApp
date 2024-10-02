package uz.turgunboyevjurabek.noteapp.feature.presentation.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IsEditProfileViewModel:ViewModel(){

    private val _editProfile= MutableStateFlow(false)
    val editProfile = _editProfile.asStateFlow()

    fun setIsEditProfile(boolean: Boolean){
        _editProfile.value=boolean
    }
}