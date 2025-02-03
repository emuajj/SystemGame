package com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps

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
fun StepFour(viewModel: SimulationViewModel, handleStartSimulation: () -> Unit) {
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
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Adjusting weight based on content
                .padding(bottom = 8.dp)
        ) {
            items(tasksSelected.value!!) { card ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = card.name,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = "Burst: ${card.burst}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Priority: ${card.priority}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
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

        Button(
            onClick = { handleStartSimulation() },
            enabled = isStartSimulationButtonActivated,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Start Simulation")
        }
    }
}
