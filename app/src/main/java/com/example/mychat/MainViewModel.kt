package com.example.mychat

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    var profile = mutableStateOf(Profile())
    var firebase = FirebaseRepo()
    var loginLauncherFlow = mutableStateOf(Any())

    var name = mutableStateOf("")
    var mail = mutableStateOf("")
    var phone = mutableStateOf("")
    var address = mutableStateOf("")

    var image = mutableStateOf<Uri?>(null)
    var listOfUsers = mutableStateOf(mutableListOf(Profile()))

    fun sendProfile(){
        firebase.sendProfile(profile.value)
    }

    fun getProfile(){
        viewModelScope.launch {
            listOfUsers.value = firebase.getProfile().toMutableList().also{
                Log.e(" Viewmodel Profile", it.toString())
            }
        }
    }
}