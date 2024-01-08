package com.joanjaume.myapplication.android.`view-models`

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.game_models.CountdownData
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountdownViewModel() : ViewModel() {
    private val inicializeDeck = arrayOf(
        CardGeneric("Card 1", CardType.TASK, "Description for Card 1", "TASK"),
        CardGeneric("Card 2", CardType.TASK, "Description for Card 2", "TASK"),
        CardGeneric("Card 3", CardType.TASK, "Description for Card 3", "TASK"),
        CardGeneric("Card 4", CardType.TASK, "Description for Card 4", "TASK"),
        CardGeneric("Card 5", CardType.TASK, "Description for Card 5", "TASK")
    )

    //    private val countdownData = MutableLiveData<CountdownData>(inicializeDeck)
//    private val _deck = MutableStateFlow(Deck(inicializeDeck))
//    val deck = _deck.asStateFlow()
    val countdownData = MutableLiveData<CountdownData>()

    fun addCard() {
        countdownData.value?.addCard()
    }

//    fun getDeck(): Array<CardGeneric> {
//        return arrayOf(
//            CardGeneric("Card 1", "Type A", "Description for Card 1", "John Doe"),
//            CardGeneric("Card 2", "Type B", "Description for Card 2", "Jane Doe"),
//            CardGeneric("Card 3", "Type C", "Description for Card 3", "Alice Smith"),
//            CardGeneric("Card 4", "Type A", "Description for Card 4", "Bob Johnson"),
//            CardGeneric("Card 5", "Type B", "Description for Card 5", "Eve Brown")
//        )
//    }

    fun getDeck(): Array<CardGeneric> {
        return countdownData.value?.getDeck() ?: arrayOf(
            CardGeneric(
                "Card 1",
                CardType.TASK,
                "Description for Card 1",
                "John Doe"
            )
        )
//        return deck.value?.getDeck() ?:
    }
}