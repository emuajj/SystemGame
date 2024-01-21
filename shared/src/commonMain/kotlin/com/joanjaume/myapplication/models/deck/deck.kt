package com.joanjaume.myapplication.models.deck

import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric

class Deck() {
    private var deckCards = listOf<ICardGeneric>()

    init {
    }

    fun getDeck(): List<ICardGeneric> {
        return deckCards
    }

    fun setDeck(cardList: List<ICardGeneric>? = null) {
        if (cardList != null) {
            deckCards = cardList
        }
    }

    fun addCard(card : ICardGeneric) {
        deckCards += card
    }
}