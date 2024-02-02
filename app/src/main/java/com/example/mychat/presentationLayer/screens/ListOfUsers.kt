package com.example.mychat.presentationLayer.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mychat.presentationLayer.viewModel.MainViewModel
import com.example.mychat.dataLayer.Profile
import com.example.mychat.domainLayer.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfUsers(mainViewModel: HiltViewModel, nav: NavHostController) {

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
                Log.e("IMG",it.displayImage.toString())
            }
        }

    }

}

@Composable
fun ProfileCard(profile: Profile, nav: NavHostController, mainViewModel: HiltViewModel) {
    Card(
        modifier = Modifier.clickable {
            mainViewModel.friendMail.value = profile.mail
            nav.navigate("MyChat")
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier.border(2.dp,Color.Green, shape = RectangleShape)
                )
                    {
                    AsyncImage(
                        model = profile.displayImage.toUri(),
                        contentDescription = "badhai ho",
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }
            }


            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Username: ${profile.name}")
                //modifier = Modifier.border()
                Text(text = "Gmail: ${profile.mail}")

            }
        }

    }
}


