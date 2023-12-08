package com.joanjaume.myapplication.android.`view-models`

import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric

class DeckViewModel {

    fun getDeck(): Array<CardGeneric> {
        return arrayOf(
            CardGeneric("Card 1", "Type A", "Description for Card 1", "John Doe"),
            CardGeneric("Card 2", "Type B", "Description for Card 2", "Jane Doe"),
            CardGeneric("Card 3", "Type C", "Description for Card 3", "Alice Smith"),
            CardGeneric("Card 4", "Type A", "Description for Card 4", "Bob Johnson"),
            CardGeneric("Card 5", "Type B", "Description for Card 5", "Eve Brown")
        )
    }
}