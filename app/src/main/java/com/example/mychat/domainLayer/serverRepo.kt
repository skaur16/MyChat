package com.example.mychat.domainLayer

import com.example.mychat.dataLayer.ArrayOfMessage
import com.example.mychat.dataLayer.Message
import com.example.mychat.dataLayer.Profile

interface serverRepo {

    fun sendProfile(profile : Profile)
    fun getProfile() : List<Profile>
    fun sendMessage(message: Message, groupId: String , groupIdReverse : String)
    fun getMessage(groupId : String, groupIdReverse: String) : ArrayOfMessage?

}