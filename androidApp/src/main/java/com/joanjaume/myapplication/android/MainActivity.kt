package com.joanjaume.myapplication.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joanjaume.myapplication.android.Activities.SimulationActivity
import com.joanjaume.myapplication.android.screens.CountdownScreen
import com.joanjaume.myapplication.android.components.card.Layout.MenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    MaterialTheme() {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") {
                MenuScreen(navController = navController)
            }
            composable("countdown") {
                // Placeholder for Countdown game screen
                // You can replace it with your Countdown game Composable
                CountdownScreen()
            }
            composable("simulation") {
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    context.startActivity(Intent(context, SimulationActivity()::class.java))
                    // Optionally, pop back or handle navigation stack if necessary
                    navController.popBackStack()
                }
            }
        }
    }
}
