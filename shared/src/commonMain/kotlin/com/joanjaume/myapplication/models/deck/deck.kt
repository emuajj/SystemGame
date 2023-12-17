package com.joanjaume.myapplication.models.deck

import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric

class Deck(initializeDeck: Array<CardGeneric>) {
    private var deckCards = arrayOf<CardGeneric>()

    init {
        if (initializeDeck.isNotEmpty()) {
            deckCards = initializeDeck
        }
    }

    fun getDeck(): Array<CardGeneric> {
        return deckCards
    }

    fun setDeck(cardArray: Array<CardGeneric>? = null) {
        if (cardArray != null) {
            deckCards = cardArray
        }
        print(deckCards)
    }

    fun addCard()
    {
        deckCards += CardGeneric(
            "Card 1",
            "Type A",
            "Description for Card 1",
            "John Doe"
        )
        print(deckCards)
    }
}