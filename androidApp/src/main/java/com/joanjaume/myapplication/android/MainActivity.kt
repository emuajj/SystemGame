package com.joanjaume.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.components.ganttTable.GanttChartComponent
import com.joanjaume.myapplication.android.`view-models`.CountdownViewModel
import com.joanjaume.myapplication.models.gameModels.CountdownData
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = remember { CountdownViewModel(CountdownData("ez")) }
                    val state by viewModel.countDownUiState.collectAsState()
                    val cpuCardEmpty = CpuCard(
                        null,
                        "--",
                        CardType.CPU,
                        "--",
                        "--",
                        0
                    )

                    Column(modifier = Modifier.fillMaxSize()) {
                        Row() {
                            state.cpuCard?.let { cpuCard ->
                                CardComposable(
                                    card = cpuCard,
                                    handleClickCard = { viewModel.handleClickCard(cpuCard) }
                                )
                            } ?: run {
                                CardComposable(
                                    card = cpuCardEmpty,
                                    handleClickCard = { viewModel.handleClickCard(cpuCardEmpty) }
                                )
                            }
                            Box() {
                                Button(
                                    onClick = { viewModel.addCard() },
                                ) {
                                    Text(text = "Press to add card")
                                }
                                Text(text = state.countdownSeconds.toString())
                            }
                        }
                        // Give GanttChartComponent a weight so it doesn't consume all vertical space
                        GanttChartComponent(
                            tasks = state.ganttTasks,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Adjust the weight as needed to balance with the LazyRow
                            maxTime = 6 // Assume 24 as max time, adjust as necessaryy
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
    }
}
