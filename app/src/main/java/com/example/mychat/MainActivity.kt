package com.example.mychat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mychat.presentationLayer.screens.Chat
import com.example.mychat.presentationLayer.screens.ListOfUsers
import com.example.mychat.presentationLayer.screens.UserForm
import com.example.mychat.presentationLayer.viewModel.MainViewModel
import com.example.mychat.ui.theme.MyChatTheme
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val Pick = registerForActivityResult(ActivityResultContracts.PickVisualMedia())
    { uri ->
         var uriCopy = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerLoginLauncher()
        setContent {

            val mainViewModel : com.example.mychat.domainLayer.HiltViewModel =  hiltViewModel()





            MyChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "MainActivity"){
                        composable("MainActivity"){
                            App(::launchLoginFlow , mainViewModel, nav)
                        }
                        composable("UserFormScreen"){ UserForm(mainViewModel , nav ,Pick) }
                        composable("ListOfUsers"){ ListOfUsers(mainViewModel,nav) }
                        composable("MyChat"){ Chat(mainViewModel) }
                    }

                }
            }
        }
    }
    // STEP 1:
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>
    private fun registerLoginLauncher() {
        Log.d("TAG", "Inside setupLoginLauncher")
        loginLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                Log.d("TAG", "Inside ActivityResult $result")
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("TAG", "Inside ResultLambda ")
                    loginHandler()
                } else Toast.makeText(this, "Not able to Login, Try Again", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    // Step 2: Launcher
    private fun launchLoginFlow(loginHandler: (() -> Unit)) {
        this.loginHandler = loginHandler

        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .build()

        loginLauncher.launch(intent)
    }

    // Step 3: Handler (to get the result)
    private lateinit var loginHandler: (() -> Unit)
}

@Composable
fun App(
    launcherLoginFlow: (() -> Unit) -> Unit,
    mainViewModel: com.example.mychat.domainLayer.HiltViewModel,
    nav: NavHostController
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(onClick = {

            launcherLoginFlow {
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    Log.e("TAG", "FirebaseAuth :- ${user.email}")
                    Log.e("TAG", "FirebaseAuth :- ${user.displayName}")
                    Log.e("TAG", "FirebaseAuth :- ${user.photoUrl}")
                    Log.e("TAG", "FirebaseAuth :- ${user.providerId}")
                    Log.e("TAG", "FirebaseAuth :- ${user.uid}")

                    mainViewModel.userMail.value = user.email.toString()


                }
                nav.navigate("UserFormScreen")



            }
        }) {
            Text(text = "Log In With Google")


        }
    }

}

