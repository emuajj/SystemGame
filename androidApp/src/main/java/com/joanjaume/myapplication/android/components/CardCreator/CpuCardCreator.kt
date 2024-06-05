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
fun CpuCardCreator(viewModel: SimulationViewModel) {
    // Fetch options from the ViewModel
    var algorithmOptions by remember { mutableStateOf(emptyMap<String, Int>()) }
    var modalityOptions by remember { mutableStateOf(emptyMap<String, Int>()) }

    // Remember the local selection state
    var selectedAlgorithmOption by remember { mutableStateOf<Int?>(null) }
    var selectedModalityOption by remember { mutableStateOf<Int?>(null) }


    // Use LaunchedEffect to fetch options once when the composable is first composed
    LaunchedEffect(key1 = true) {
        algorithmOptions = viewModel.getAlgorithmOptions()
        modalityOptions = viewModel.getModalityOptions()
    }

    // Handle local selection changes
    fun onChangeAlgorithm(name: String, value: Int) {
//        selectedOption = name to value
        println("Local selection: $name with value $value")
        selectedAlgorithmOption = value
    }

    fun onChangeModality(name: String, value: Int) {
//        selectedOption = name to value
        println("Local selection: $name with value $value")
        selectedModalityOption = value
    }

    // Conditionally display the SelectorComposable based on the availability of options
    if (algorithmOptions.isNotEmpty() && modalityOptions.isNotEmpty()) {
        SelectorComposable(
            options = algorithmOptions,
            label = "Select Algorithm",
            onOptionSelected = ::onChangeAlgorithm
        )
        SelectorComposable(
            options = modalityOptions,
            label = "Select Algorithm",
            onOptionSelected = ::onChangeModality
        )
    } else {
        // Optionally display a placeholder or a loading indicator when options are empty
        Text("Loading algorithms...", style = MaterialTheme.typography.body1)
    }

    // Example button to save the selection to the ViewModel
    Button(
        onClick = {
            // Ensure non-null values when constructing the card object
            selectedModalityOption?.let { modality ->
                selectedAlgorithmOption?.let { algorithm ->
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
        },
        enabled = selectedModalityOption != null && selectedAlgorithmOption !== null
    ) {
        Text("Save Card")
    }
}
