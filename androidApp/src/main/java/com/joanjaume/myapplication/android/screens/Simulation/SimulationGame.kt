package com.joanjaume.myapplication.android.screens.Simulation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joanjaume.myapplication.android.MyApplicationTheme
import com.joanjaume.myapplication.android.ViewModels.SimulationGameViewModel
import com.joanjaume.myapplication.android.components.card.AlgorithmCard.AlgorithmCardComposable
import com.joanjaume.myapplication.android.components.card.CpuCard.CpuCardComposable
import com.joanjaume.myapplication.android.components.ganttTable.GanttChartComponent
import com.joanjaume.myapplication.android.components.ui.Results.ShowResultsModal
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

@Composable
fun SimulationGame(tasksList: List<TaskCard>, cpuCard: CpuCard, algorithmCard: AlgorithmCard) {
    val viewModel = SimulationGameViewModel(taskCards = tasksList, cpuCard, algorithmCard)

    val timeCount = viewModel.timeCount.observeAsState(0)
    val ganttTasks = viewModel.ganttTasks.observeAsState(emptyList())
    val results = viewModel.results.observeAsState(null)
    val isResultsModalOpen = viewModel.isResultsModalOpen.observeAsState(false)

    MyApplicationTheme()
    {

        if (results.value != null && isResultsModalOpen.value) {
            Dialog(onDismissRequest = { viewModel.toggleResultsModal(false) }) {
                ShowResultsModal(
                    toggleResultsModal = { value -> viewModel.toggleResultsModal(value) },
                    results = viewModel.results.value!!
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row() {
                    Column() {
                        CpuCardComposable(
                            card = cpuCard,
                            handleClickCard = { Unit }
                        )
                        AlgorithmCardComposable(
                            card = algorithmCard,
                            handleClickCard = { Unit })

                    }
                    Box(
                        contentAlignment = Alignment.Center // Aligns children in the center of the Box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally in the column
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Provides space between children in the column
                        ) {
                            Button(
                                onClick = { viewModel.changeTime("acc") }
                            ) {
                                Text(text = "x2")
                            }
                            Button(
                                onClick = { viewModel.changeTime("dec") }
                            ) {
                                Text(text = ":2")
                            }
                        }
                    }

                }
                if (ganttTasks.value.isNotEmpty()) {
                    GanttChartComponent(
                        tasks = ganttTasks.value,
                        iteration = timeCount.value,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f), // Adjust the weight as needed to balance with the LazyRow
                        maxTime = 100 // Assume 100 as max time, adjust as necessaryy
                    )

                }
            }
        }
    }
}