package com.joanjaume.myapplication.android.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeckViewModel: ViewModel(){

    private val _deck = MutableStateFlow<List<CardGeneric>>(emptyList())
    val deck: StateFlow<List<CardGeneric>> = _deck

    init {
        loadCards()
    }

    private fun loadCards() {
        _deck.value = listOf(
            CardGeneric("Card 1", "Type A", "Description for Card 1", "CPU"),
            CardGeneric("Card 2", "Type B", "Description for Card 2", "Jane Doe"),
            CardGeneric("Card 3", "Type C", "Description for Card 3", "Alice Smith"),
            CardGeneric("Card 4", "Type A", "Description for Card 4", "Bob Johnson"),
            CardGeneric("Card 5", "Type B", "Description for Card 5", "Eve Brown")
        )
    }

     fun addCard() {
         viewModelScope.launch {
             _deck.value = _deck.value + CardGeneric(
                 "Card " + (_deck.value.size + 1).toString() + " ",
                 "Type A",
                 "Description",
                 "John Doe"
             )
         }
    }



}