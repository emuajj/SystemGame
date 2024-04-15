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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.components.gantTable.GanttChartComponent
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

                    Column() {
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
                        // Integrating GanttChartComponent with required parameters
                        GanttChartComponent(
                            tasks = state.ganttTasks,
                            chartWidth = 400.dp, // Specify your desired width
                            chartHeight = 200.dp, // Specify your desired height
                            maxTime = 24L // Assume 24 as max time, adjust as necessary
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 16.dp)
                                .wrapContentHeight(Alignment.Bottom)
                        ) {
                            items(state.deck) { card ->
                                CardComposable(
                                    card,
                                    handleClickCard = { viewModel.handleClickCard(card) }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
