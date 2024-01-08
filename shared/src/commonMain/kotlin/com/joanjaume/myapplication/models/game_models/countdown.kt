package com.joanjaume.myapplication.models.game_models

import com.joanjaume.myapplication.models.cards.`card-generic`.CardGeneric
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType

data class CountdownData(val inicializeCardDeck: Array<CardGeneric>) {
    private var deck = Deck(inicializeCardDeck)

    fun getDeck() : Array<CardGeneric>{
    return deck.getDeck()
    }

    fun addCard() {
        deck.addCard()
    }

}