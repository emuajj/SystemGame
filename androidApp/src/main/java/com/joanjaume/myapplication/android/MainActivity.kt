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

                    Column() {
                        Row() {
                            state.cpuCard?.let { cpuCard ->
                                CardComposable(card = cpuCard)
                            } ?: run {
                                CardComposable(card = CpuCard("--",CardType.CPU,"--","--",0))
                            }
                            Button(onClick = { viewModel.addCard() }) {

                            }
                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 16.dp)
                                .wrapContentHeight(Alignment.Bottom)
                        ) {
                            items(state.deck) { card ->
                                CardComposable(card)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
