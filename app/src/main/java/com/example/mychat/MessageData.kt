package com.example.mychat

data class Message(
    var message :String = ""
)

data class ArrayOfMessage(
    var messageArray : List<Message> = listOf()
)
