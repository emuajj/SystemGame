package com.joanjaume.myapplication.android.components.CardCreator

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun showModalForQuantumInput(onQuantumSelected: (Int) -> Unit) {
    // State to manage if the dialog is shown
    val showDialog = remember { mutableStateOf(true) }
    // State to hold the entered quantum value
    var quantumInput by remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Enter Quantum Value")
            },
            text = {
                TextField(
                    value = quantumInput,
                    onValueChange = { quantumInput = it },
                    label = { Text("Quantum") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onQuantumSelected(quantumInput.toIntOrNull() ?: 0)
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
