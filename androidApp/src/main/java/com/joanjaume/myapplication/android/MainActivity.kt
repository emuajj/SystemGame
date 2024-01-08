package com.joanjaume.myapplication.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric
import com.joanjaume.myapplication.android.viewModels.DeckViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                val flowDeck by viewModels()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainScreen()
                    }
                }
            }
        }
    }

@Composable
fun mainScreen(){
    val viewModel = remember { DeckViewModel()}
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        CardList(viewModel = viewModel)
        AddCardButton(viewModel = viewModel)
    }
}

@Composable
fun AddCardButton(viewModel: DeckViewModel) {
    FloatingActionButton(
        onClick = { viewModel.addCard()},
        modifier = Modifier
            .padding(16.dp)
            .size(56.dp),
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Text(text = "+")
    }
}

@Composable
fun CardList(viewModel: DeckViewModel) {
    val deck by viewModel.deck.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Deck")
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(deck) { card ->
                CardCell(card = card)
            }
        }
    }


}


@Composable
fun CardCell(card: CardGeneric) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    )
    {
        CardComposable(card = card)
    }
}