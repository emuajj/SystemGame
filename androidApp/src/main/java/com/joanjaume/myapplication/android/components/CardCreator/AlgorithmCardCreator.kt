package com.joanjaume.myapplication.android.components.CardCreator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.android.components.ui.Selector.SelectorComposable
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType

@Composable
fun AlgorithmCardCreator(viewModel: SimulationViewModel) {
    var algorithmOptions by remember { mutableStateOf(emptyMap<String, Int>()) }
    var modalityOptions by remember { mutableStateOf(emptyMap<String, Int>()) }
    var selectedAlgorithmOption by remember { mutableStateOf(1) }
    var selectedModalityOption by remember { mutableStateOf(1) }
    var algorithmName by remember { mutableStateOf("") }
    var showQuantumModal by remember { mutableStateOf(false) }  // State to control modal visibility

    // Remember the scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        algorithmOptions = viewModel.getAlgorithmOptions()
        modalityOptions = viewModel.getModalityOptions()
    }

    // Apply vertical scroll modifier
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the whole screen
            .verticalScroll(scrollState) // Add vertical scroll
            .padding(16.dp) // Optional: add some padding
    ) {
        if (algorithmOptions.isNotEmpty() && modalityOptions.isNotEmpty()) {
            TextField(
                value = algorithmName,
                onValueChange = { algorithmName = it },
                label = { Text("Enter a name for the algorithm") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 8.dp) // Optional: add some padding between elements
            )
            SelectorComposable(
                options = algorithmOptions,
                label = "Select Algorithm",
                onOptionSelected = { name, value -> selectedAlgorithmOption = value }
            )
            Spacer(modifier = Modifier.height(8.dp)) // Optional: add some space between components
            SelectorComposable(
                options = modalityOptions,
                label = "Select Modality",
                onOptionSelected = { name, value -> selectedModalityOption = value }
            )
            Spacer(modifier = Modifier.height(16.dp)) // Optional: add some space before the button
        } else {
            Text("Loading algorithms...", style = MaterialTheme.typography.body1)
        }

        Button(
            onClick = {
                if (viewModel.getAlgorithmByName("Round Robin") == selectedAlgorithmOption) {
                    showQuantumModal = true  // Set state to show modal
                } else {
                    // Save logic for other cases
                    selectedModalityOption.let { modality ->
                        selectedAlgorithmOption.let { algorithm ->
                            viewModel.saveNewCard(
                                AlgorithmCard(
                                    id = null,
                                    name = algorithmName,
                                    description = "New Card",
                                    type = CardType.ALGORITHM,
                                    modality = modality,
                                    algorithm = algorithm
                                )
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth() // Optional: make the button fill the width
        ) {
            Text("Save Card")
        }
    }

    // Check state and show modal if true
    if (showQuantumModal) {
        showModalForQuantumInput { quantum ->
            showQuantumModal = false  // Hide modal after selection
            selectedModalityOption.let { modality ->
                selectedAlgorithmOption.let { algorithm ->
                    viewModel.saveNewCard(
                        AlgorithmCard(
                            id = null,
                            name = algorithmName,
                            description = "New Card",
                            type = CardType.ALGORITHM,
                            modality = modality,
                            algorithm = algorithm,
                            quantum = quantum
                        )
                    )
                }
            }
        }
    }
}
