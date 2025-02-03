package com.joanjaume.myapplication.android.components.CardCreator

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard


//override val id: Int?,
//override val name: String,
//override val type: CardType = CardType.CPU,
//override val description: String,
//override val clockSpeed: Int,

@Composable
fun CpuCardCreator(viewModel: SimulationViewModel) {

    // Remember the local selection state
    var selectedClockSpeed by remember { mutableStateOf(1) }
    var cardName by remember { mutableStateOf("") }

    fun onChangeClokSpeed(name: String, value: Int) {
//        selectedOption = name to value
        println("Local selection: $name with value $value")
        selectedClockSpeed = value
    }
    TextField(
        value = selectedClockSpeed.toString(),
        onValueChange = { selectedClockSpeed = it.toInt() },
        label = { Text("ClockSpeed") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(16.dp)) // Optional: add some space before the button
    TextField(value = cardName, onValueChange = { cardName = it }, label = {Text(text = "Choose a name")})
    // Example button to save the selection to the ViewModel
    Button(
        onClick = {
            viewModel.saveNewCard(
                CpuCard(
                    id = null,
                    name = cardName,
                    description = "New Card",
                    type = CardType.CPU,
                    clockSpeed = selectedClockSpeed
                )
            )
        },
    ) {
        Text("Save Card")
    }
}
