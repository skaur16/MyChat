package com.example.mychat.domainLayer

import com.example.mychat.dataLayer.ArrayOfMessage
import com.example.mychat.dataLayer.Message
import com.example.mychat.dataLayer.Profile

interface serverRepo {

    suspend fun sendProfile(profile : Profile)
    suspend fun getProfile() : List<Profile>
    suspend fun sendMessage(message: Message, groupId: String , groupIdReverse : String)
    suspend fun getMessage(groupId : String, groupIdReverse: String) : ArrayOfMessage?

}