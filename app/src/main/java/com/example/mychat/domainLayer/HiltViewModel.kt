package com.example.mychat.domainLayer

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychat.FirebaseRepo
import com.example.mychat.dataLayer.Message
import com.example.mychat.dataLayer.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiltViewModel @Inject constructor(
    private var serverRepoRef: ServerRepo
) : ViewModel() {



    var profile = mutableStateOf(Profile())
    var firebase = FirebaseRepo()
    var loginLauncherFlow = mutableStateOf(Any())


    var name = mutableStateOf("")
    var mail = mutableStateOf("")
    var phone = mutableStateOf("")
    var address = mutableStateOf("")

    var image = mutableStateOf<Uri?>(null)


    var listOfUsers = mutableStateOf(mutableListOf(Profile()))

    var getMyChat = mutableStateOf(false)
    var message = mutableStateOf(Message())
    var text = mutableStateOf("")
    var listOfMessage = mutableStateOf(listOf<Message>())

    var userMail = mutableStateOf("")
    var friendMail = mutableStateOf("")
    var msg = mutableStateOf("")
    var groupId = mutableStateOf("${userMail} and ${friendMail}")
    var groupIdReverse = mutableStateOf("${friendMail} and ${userMail}")







            fun sendProfile() {
                viewModelScope.launch {
                    serverRepoRef.sendProfile(profile.value)
                }

            }

            fun getProfile() {
                viewModelScope.launch {
                    listOfUsers.value = serverRepoRef.getProfile().toMutableList().also {
                        Log.e(" Viewmodel Profile", it.toString())
                    }
                }
            }

            fun sendMessage() {
                message.value = message.value.copy(
                    message = msg.value
                )

                viewModelScope.launch {
                    serverRepoRef.sendMessage(message.value, groupId.value, groupIdReverse.value)
                }
            }

            fun getMessage() {
                viewModelScope.launch {
                    serverRepoRef.getMessage(groupId.value, groupIdReverse.value).also {
                        if (it != null) {
                            listOfMessage.value = it.messageArray
                        }
                    }
                }
            }



}
