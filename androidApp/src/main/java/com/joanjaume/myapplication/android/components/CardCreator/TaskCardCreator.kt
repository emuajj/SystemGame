package com.joanjaume.myapplication.android.components.CardCreator

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
    var burst by remember { mutableStateOf("") } // Default burst value
    var priority by remember { mutableStateOf("") } // Default priority value
    var name by remember { mutableStateOf("") } // Default priority value
    var io by remember { mutableStateOf("") } // Default priority value
    val context = LocalContext.current

    // Row for placing text fields side by side with equal weight
    Row {
        TextField(
            value = name,
            onValueChange = { newValue ->
                name = newValue
            },
            label = { Text("Enter a name for the card") },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )
        Spacer(Modifier.width(16.dp)) // Adds space between the two text fields
        TextField(
            value = io.toString(),
            onValueChange = { newValue ->
                io = newValue
            },
            label = { Text("I/O") },
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )

    }
    Spacer(modifier = Modifier.height(20.dp))
    Row {
        TextField(
            value = burst.toString(),
            onValueChange = { newValue ->
                burst = newValue
            },
            label = { Text("Enter Burst Value") },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )

        Spacer(Modifier.width(16.dp)) // Adds space between the two text fields

        TextField(
            value = priority.toString(),
            onValueChange = { newValue ->
                priority = newValue
            },
            label = { Text("Enter Priority Value") },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.9f) // Make TextField flexible and use 90% of the available space
        )
    }
    Text(text = "Note that a lower number means higher priority.")

    // Button to save the new task card
    Button(
        onClick = {
            viewModel.saveNewCard(
                TaskCard(
                    id = null,
                    name = if (name.isEmpty()) "User's card" else name,
                    description = "New Task Card",
                    type = CardType.TASK,
                    burst = if (name.isEmpty()) 1 else burst.toInt(),
                    priority = if (name.isEmpty()) 1 else priority.toInt(),
                    arriveTime = 0
                )
            )
        }
    ) {
        Text("Save Card")
    }
}
