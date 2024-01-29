package com.example.mychat.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mychat.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(mainViewModel: MainViewModel) {
    Column() {
        TopAppBar(title = {
            Text(text = "My Chat")
            TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue)
        })
        Row() {
            Button(onClick = { mainViewModel.getMyChat.value = true }) {
                Text(text = "Get my chat")
            }
        }
        if (mainViewModel.getMyChat.value) {
           mainViewModel.getMessage()
            LazyColumn() {
                items(mainViewModel.listOfMessage.value){
                   Card (){
                       Text(text=it.message)
                   }

                }
                //Text(text="")
            }
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            value = mainViewModel.text.value ,
            onValueChange = {
            mainViewModel.text.value = it
                mainViewModel.msg.value = mainViewModel.text.value
                            },
        )
        Button(onClick = {
            mainViewModel.sendMessage()
            mainViewModel.text.value = ""
        }) {
            Text(text = "Send")
        }
    }

}