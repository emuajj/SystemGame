package com.joanjaume.myapplication.android.screens

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.MyApplicationTheme
import com.joanjaume.myapplication.android.components.card.AlgorithmCard.AlgorithmCardComposable
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.components.card.CpuCard.CpuCardComposable
import com.joanjaume.myapplication.android.components.ganttTable.GanttChartComponent
import com.joanjaume.myapplication.android.`view-models`.ViewModel
import com.joanjaume.myapplication.models.gameModels.Model
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard

@Composable
fun CountdownScreen() {
    val viewModel = remember { ViewModel(Model()) } // Initialize your ViewModel here
    val state by viewModel.modelUiState.collectAsState()

    MyApplicationTheme()
    {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val cpuCardEmpty = CpuCard(
                null,
                "--",
                CardType.CPU,
                "--",
                1
            )
            val algorithmCardEmpty = AlgorithmCard(
                null,
                "--",
                null,
                CardType.ALGORITHM,
                1,
                1
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Row() {
                    Column() {
                        state.cpuCard?.let { cpuCard: CpuCard ->
//                                CardComposable(
//                                    card = cpuCard,
//                                    handleClickCard = { viewModel.handleClickCard(cpuCard) }
//                                )
                            CpuCardComposable(
                                card = cpuCard,
                                handleClickCard = { viewModel.handleClickCard(cpuCard) }
                            )
                        } ?: run {
                            CpuCardComposable(
                                card = cpuCardEmpty,
                                handleClickCard = { viewModel.handleClickCard(cpuCardEmpty) }
                            )
                        }
                        state.algorithmCard?.let { algorithmCard: AlgorithmCard ->
//                                CardComposable(
//                                    card = cpuCard,
//                                    handleClickCard = { viewModel.handleClickCard(cpuCard) }
//                                )
                            AlgorithmCardComposable(
                                card = algorithmCard,
                                handleClickCard = { viewModel.handleClickCard(algorithmCard) }
                            )
                        } ?: run {
                            AlgorithmCardComposable(
                                card = algorithmCardEmpty,
                                handleClickCard = {
                                    viewModel.handleClickCard(
                                        algorithmCardEmpty
                                    )
                                }
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center // Aligns children in the center of the Box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally in the column
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Provides space between children in the column
                        ) {
                            Button(
                                onClick = { viewModel.addCard() }
                            ) {
                                Text(text = "Press to add card")
                            }
                            Button(
                                onClick = { viewModel.iterateTime() }
                            ) {
                                Text(text = "IterateTime")
                            }
                            Text(text = "Time left :" + state.countdownSeconds.toString())
                            Text(text = "Iteration :" + state.timeCount.toString())
                        }
                    }

                }
                // Give GanttChartComponent a weight so it doesn't consume all vertical space
                GanttChartComponent(
                    tasks = state.ganttTasks,
                    iteration = state.timeCount,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Adjust the weight as needed to balance with the LazyRow
                    maxTime = 100 // Assume 24 as max time, adjust as necessaryy
                )
                // Ensure LazyRow is always visible at the bottom
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f) // Adjust this weight to control its vertical space
                ) {
                    items(state.deck) { card ->
                        CardComposable(
                            card,
                            handleClickCard = { viewModel.handleClickCard(card) }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}