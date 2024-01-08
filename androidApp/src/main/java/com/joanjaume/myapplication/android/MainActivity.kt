package com.joanjaume.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.`view-models`.CountdownViewModel


class MainActivity : ComponentActivity() {
    private val countdownViewModel = CountdownViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val cardDeck: MutableList<CardGeneric> = viewModel.getDeck().toMutableList();
        setContent {
            MyApplicationTheme {
//                val flowDeck by viewModels()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        Button(onClick = { countdownViewModel.addCard() }) {

                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
//                                .align()
                        ) {
                            items(countdownViewModel.getDeck()) { card ->
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
