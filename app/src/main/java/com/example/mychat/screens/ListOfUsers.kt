package com.example.mychat.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mychat.MainViewModel
import com.example.mychat.Profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfUsers(mainViewModel: MainViewModel,nav: NavHostController) {

    Column() {
        TopAppBar(title = {
            Text(text = "Chats")
            TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Green
            )
        })
        LazyColumn {

            items(mainViewModel.listOfUsers.value) {
                ProfileCard(it,nav,mainViewModel)
            }
        }

    }

}

@Composable
fun ProfileCard(profile: Profile, nav: NavHostController, mainViewModel: MainViewModel) {
    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column() {


                AsyncImage(model = mainViewModel.image.value,
                    contentDescription = "badhai ho" )
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Username: ${profile.name}")
                //modifier = Modifier.border()
                Text(text = "Gmail: ${profile.mail}")
                Button(onClick = {
                    mainViewModel.friendMail.value = profile.mail
                    nav.navigate("MyChat")
                }) {
                    Text(text = "Chat")
                }
            }
        }
        Divider(
            thickness = 2.dp,
            color = Color.Black
        )
    }
}


