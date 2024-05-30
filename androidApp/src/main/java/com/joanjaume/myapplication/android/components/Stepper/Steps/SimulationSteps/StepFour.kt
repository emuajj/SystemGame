package com.joanjaume.myapplication.android.components.Stepper.Steps.SimulationSteps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard

@Composable
fun StepFour(viewModel: SimulationViewModel, handleStartSimulation: Unit) {
    var isStartSimulationButtonActivated by remember { mutableStateOf(false) }
    val tasksSelected = viewModel.selectedTaskCards.observeAsState(emptyList())
    val selectedAlgorithmCard =
        viewModel.selectedAlgorithmCard.observeAsState(initial = null as AlgorithmCard?)
    val selectedCpuCard =
        viewModel.selectedCpuCard.observeAsState(initial = null as CpuCard?)

    LaunchedEffect(selectedAlgorithmCard.value, selectedCpuCard.value, tasksSelected.value) {
        isStartSimulationButtonActivated =
            viewModel.selectedAlgorithmCard.value != null && viewModel.selectedCpuCard.value != null && viewModel.selectedTaskCards.value?.isNotEmpty() == true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color(0xFF006400), RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tasks(${tasksSelected.value.size}) for the simulation:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bold
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        if (tasksSelected.value?.isNotEmpty() == true) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()  // Ensures the LazyColumn fills the horizontal space
                    .heightIn(min = 100.dp, max = 300.dp) // Restricts the height, change values as needed
                    .wrapContentHeight() // Adjusts height based on content
            ) {
                items(tasksSelected.value!!) { card ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()  // Ensures the Row fills the horizontal space
                            .padding(2.dp)  // Adds padding around each Row for better spacing
                            .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = card.name,
                            modifier = Modifier
                                .weight(1f)  // Allocates a fraction of the Row's space to this Text
                                .padding(end = 8.dp)  // Adds padding to the end of the Text
                        )
                        Text(
                            text = "Burst: ${card.burst}",
                            modifier = Modifier
                                .weight(1f)  // Ensures this Text also takes a significant portion of the space
                        )
                        Text(
                            text = "Priority: ${card.priority}",
                            modifier = Modifier
                                .weight(1f)  // Ensures this Text also takes a significant portion of the space
                        )
                    }
                }
            }
        } else {
            Text(
                text = "NO SELECTED CARDS YET",
                style = TextStyle(
                    fontSize = 20.sp, // Change the font size to 20 sp
                    fontWeight = FontWeight.Light // Optional: make the text bold
                ),
                modifier = Modifier.padding(8.dp) // Adds padding around the text
            )
        }
        Text(
            text = "Algorithm for the simulation:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bold
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        if (selectedAlgorithmCard.value != null) {
            CardComposable(
                selectedAlgorithmCard.value!!,
                handleClickCard = { Unit }
            )
        } else {
            Text(
                text = "NO SELECTED CARD YET",
                style = TextStyle(
                    fontSize = 20.sp, // Change the font size to 20 sp
                    fontWeight = FontWeight.Light // Optional: make the text bold
                ),
                modifier = Modifier.padding(8.dp) // Adds padding around the text
            )
        }
        Text(
            text = "CPU for the simulation:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bold
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        if (selectedCpuCard.value != null) {
            CardComposable(
                selectedCpuCard.value!!,
                handleClickCard = { Unit }
            )
        } else {
            Text(
                text = "NO SELECTED CARD YET",
                style = TextStyle(
                    fontSize = 20.sp, // Change the font size to 20 sp
                    fontWeight = FontWeight.Light // Optional: make the text bold
                ),
                modifier = Modifier.padding(8.dp) // Adds padding around the text
            )
        }
        Button(onClick = { handleStartSimulation }, enabled = isStartSimulationButtonActivated) {
            Text(text = "Start Simulation")
        }
    }
}
