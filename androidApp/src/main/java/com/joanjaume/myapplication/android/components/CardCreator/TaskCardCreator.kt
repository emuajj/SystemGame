package com.joanjaume.myapplication.android.components.CardCreator

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

@Composable
fun TaskCardCreator(viewModel: SimulationViewModel) {
    var burst by remember { mutableStateOf(1) } // Default burst value
    var priority by remember { mutableStateOf(1) } // Default priority value
    val context = LocalContext.current

    // Row for placing text fields side by side with equal weight
    Row {
        TextField(
            value = burst.toString(),
            onValueChange = { newValue ->
                burst = newValue.toIntOrNull() ?: 0 // Default to 1 if conversion fails
                if (newValue.toIntOrNull() == null && newValue.isNotEmpty()) {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Enter Burst Value") },
            modifier = Modifier.weight(1f).fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )

        Spacer(Modifier.width(16.dp)) // Adds space between the two text fields

        TextField(
            value = priority.toString(),
            onValueChange = { newValue ->
                priority = newValue.toIntOrNull() ?: 0 // Default to 1 if conversion fails
                if (newValue.toIntOrNull() == null && newValue.isNotEmpty()) {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Enter Priority Value") },
            modifier = Modifier.weight(1f).fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )
    }

    // Button to save the new task card
    Button(
        onClick = {
            viewModel.saveNewCard(
                TaskCard(
                    id = null,
                    name = "JJs Task Card",
                    description = "New Task Card",
                    type = CardType.TASK,
                    burst = burst,
                    priority = priority,
                    arriveTime = 0
                )
            )
        },
        enabled = (burst > 0 && priority > 0) // Enable button only if both burst and priority are valid and greater than 0
    ) {
        Text("Save Card")
    }
}
