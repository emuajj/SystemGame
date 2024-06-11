package com.joanjaume.myapplication.android.components.CardCreator

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    var showQuantumModal by remember { mutableStateOf(false) }  // State to control modal visibility

    LaunchedEffect(key1 = true) {
        algorithmOptions = viewModel.getAlgorithmOptions()
        modalityOptions = viewModel.getModalityOptions()
    }

    if (algorithmOptions.isNotEmpty() && modalityOptions.isNotEmpty()) {
        SelectorComposable(
            options = algorithmOptions,
            label = "Select Algorithm",
            onOptionSelected = { name, value -> selectedAlgorithmOption = value }
        )
        SelectorComposable(
            options = modalityOptions,
            label = "Select Modality",
            onOptionSelected = { name, value -> selectedModalityOption = value }
        )
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
                                name = "JJs Card",
                                description = "New Card",
                                type = CardType.ALGORITHM,
                                modality = modality,
                                algorithm = algorithm
                            )
                        )
                    }
                }
            }
        }
    ) {
        Text("Save Card")
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
                            name = "JJs Card",
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
