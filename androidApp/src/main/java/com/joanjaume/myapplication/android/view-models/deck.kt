package com.joanjaume.myapplication.android.`view-models`

import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric

class DeckViewModel {

    fun getDeck(): Array<CardGeneric> {
        return arrayOf(
            CardGeneric("Card 1", CardType.TASK, "Description for Card 1", "PROCESS1"),
            CardGeneric("Card 2", CardType.TASK, "Description for Card 2", "PROCESS2"),
            CardGeneric("Card 3", CardType.TASK, "Description for Card 3", "PROCESS3"),
            CardGeneric("Card 4", CardType.TASK, "Description for Card 4", "PROCESS4"),
            CardGeneric("Card 5", CardType.TASK, "Description for Card 5", "PROCESS5")
        )
    }
}