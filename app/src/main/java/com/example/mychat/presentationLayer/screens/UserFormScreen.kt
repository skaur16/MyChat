package com.example.mychat.presentationLayer.screens

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mychat.domainLayer.HiltViewModel
//import coil.compose.AsyncImage
import com.example.mychat.presentationLayer.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserForm(
    mainViewModel: HiltViewModel,
    nav: NavHostController,
    Pick: ActivityResultLauncher<PickVisualMediaRequest>
) {

    Column(){
        TopAppBar(title = { Text(text = "Form")
        TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Green)
        })

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                Box(modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    ){
                    if(mainViewModel.image.value==null){
                        Button(onClick = {
                            Pick.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }) {
                            Text(text="Choose photo")
                        }
                    }
                    else{

                        //mainViewModel.getImage()
                        AsyncImage(model = mainViewModel.image.value,
                            contentDescription = "bdhai ho",
                            modifier = Modifier.width(100.dp).height(100.dp)
                            )
                    }

                }

            Box(
                modifier = Modifier.border(2.dp, Color.Green, RectangleShape)

            ) {
                Column(

                ) {
                    Row() {
                        TextField(value = mainViewModel.name.value,
                            onValueChange = {
                                mainViewModel.name.value = it
                            },
                            label = { Text(text = "Name") }
                        )
                    }

                    Row() {

                        TextField(value = mainViewModel.userMail.value,
                            onValueChange = {},
                            label = { Text(text = "Mail") }
                        )
                    }

                    Row() {


                        TextField(value = mainViewModel.phone.value,
                            onValueChange = {
                                mainViewModel.phone.value = it
                            },
                            label = { Text(text = "Phone no.") }
                        )
                    }
                    Row() {

                        TextField(value = mainViewModel.address.value,
                            onValueChange = {
                                mainViewModel.address.value = it
                            },
                            label = { Text(text = "Address") }
                        )
                    }
                }
            }
        }

         Spacer(modifier = Modifier.height(30.dp))

        Row(){
            Button(onClick = {

                mainViewModel.profile.value = mainViewModel.profile.value.copy(
                    name = mainViewModel.name.value,
                    mail = mainViewModel.userMail.value,
                    phone = mainViewModel.phone.value,
                    address = mainViewModel.address.value,
                    displayImage = mainViewModel.image.value.toString()
                )

                mainViewModel.sendProfile()

            }) {
                Text(text="Submit")
            }

            Spacer(modifier = Modifier.width( 80.dp))

            Button(onClick = {
                mainViewModel.getProfile()
                nav.navigate("ListOfUsers")
            }) {
                Text(text="View")
            }


        }

    }

}