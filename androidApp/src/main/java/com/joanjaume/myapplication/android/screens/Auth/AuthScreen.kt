package com.joanjaume.myapplication.android.screens.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.joanjaume.myapplication.android.ViewModels.AuthState
import com.joanjaume.myapplication.android.ViewModels.AuthViewModel

@Composable
fun AuthScreen(
    onLoginSuccess: (FirebaseUser?) -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()

    // Handle UI state
    when (authState) {
        is AuthState.Success -> {
            onLoginSuccess((authState as AuthState.Success).user)
        }
        is AuthState.Error -> {
            Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT)
                .show()
        }
        AuthState.Loading -> {
            // Show loading spinner (optional)
            Text("Loading...")
        }
        AuthState.Idle -> {
            // Do nothing
        }
    }

    // Main UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { authViewModel.register(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}