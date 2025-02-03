package com.joanjaume.myapplication.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.joanjaume.myapplication.android.Activities.SimulationActivity
import com.joanjaume.myapplication.android.ViewModels.AuthViewModel
import com.joanjaume.myapplication.android.screens.CountdownScreen
import com.joanjaume.myapplication.android.components.ui.Layout.MenuScreen
import com.joanjaume.myapplication.android.screens.Auth.AuthScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val auth = FirebaseAuth.getInstance()
            var user by remember { mutableStateOf(auth.currentUser) }
            val authViewModel: AuthViewModel = viewModel()

            if (user == null) {
                AuthScreen(
                    onLoginSuccess = { newUser ->
                        user = newUser
                    },
                    authViewModel = authViewModel
                )
            } else {
                MyApp()
            }
        }

    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title if needed */ },
                actions = {
                    androidx.compose.material.Button(onClick = {
                        auth.signOut()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }) {
                        androidx.compose.material.Text("Logout")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                androidx.compose.material.Text(
                    text = "You're logged as \"${user?.displayName ?: user?.email ?: "Guest"}\"",
                    modifier = androidx.compose.ui.Modifier.padding(16.dp)
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "menu",
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
        ) {
            composable("menu") {
                MenuScreen(navController = navController)
            }
            composable("countdown") {
                CountdownScreen()
            }
            composable("simulation") {
                LaunchedEffect(Unit) {
                    // Create an intent to start SimulationActivity
                    val intent = Intent(context, SimulationActivity::class.java).apply {
                        putExtra("userId", user?.uid) // Pass user ID
                        putExtra("userName", user?.displayName ?: user?.email ?: "Guest") // Optional: Pass user name or email
                    }
                    context.startActivity(intent)

                    // Optionally pop back or handle navigation stack
                    navController.popBackStack()
                }
            }
        }
    }
}

