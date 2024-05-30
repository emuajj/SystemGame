package com.joanjaume.myapplication.android.screens.Simulation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.MyApplicationTheme
import com.joanjaume.myapplication.android.ViewModels.SimulationGameViewModel
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.android.components.card.AlgorithmCard.AlgorithmCardComposable
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.components.card.CpuCard.CpuCardComposable
import com.joanjaume.myapplication.android.components.ganttTable.GanttChartComponent
import com.joanjaume.myapplication.android.ViewModels.ViewModel
import com.joanjaume.myapplication.models.gameModels.Model
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

@Composable
fun SimulationGame(tasksList: List<TaskCard>, cpuCard: CpuCard, algorithmCard: AlgorithmCard) {
    val viewModel = SimulationGameViewModel(taskcards = tasksList, cpuCard, algorithmCard)

    val timeCount = viewModel.timeCount.observeAsState(0)
    val ganttTasks = viewModel.ganttTasks.observeAsState(emptyList())

    MyApplicationTheme()
    {
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
//                            Button(
//                                onClick = { viewModel.iterateTime() }
//                            ) {
//                                Text(text = "IterateTime")
//                            }
//                            Text(text = "Iteration :" + state.timeCount.toString())
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
                        maxTime = 100 // Assume 24 as max time, adjust as necessaryy
                    )

                }
            }
        }
    }
}