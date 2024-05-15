package com.joanjaume.myapplication.models.deck

import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class Deck() {
    private var deckCards = mutableListOf<ICardGeneric>()
    private var nextDeckId = 1  // Starting ID value

    init {
    }

    fun getDeck(): List<ICardGeneric> {
        return deckCards
    }

    fun setDeck(cardList: List<ICardGeneric>? = null) {
        if (cardList != null) {
            println("PASS")

            deckCards = mutableListOf()  // Initialize a new mutable list

            // Identify the maximum ID currently in use to avoid overwriting existing IDs
            val existingIds = cardList.mapNotNull { it.cardId }.toSet()
            var maxExistingId = existingIds.maxOrNull() ?: 0

            // Start nextDeckId from the next available number after the highest existing ID
            nextDeckId = maxExistingId + 1

            // Assign new IDs to cards without an ID and add them to the deck
            cardList.forEach { card ->
                if (card.cardId == null || existingIds.contains(card.cardId)) {
                    while (existingIds.contains(nextDeckId)) {
                        nextDeckId++  // Ensure we skip any IDs that are already in use
                    }
                    card.cardId = nextDeckId++
                }
                deckCards.add(card)
                println("list OFFF : $deckCards")
            }
        }
    }


    fun addCard(card: ICardGeneric) {
        card.cardId = nextDeckId++
        deckCards.add(card)
    }

    fun removeCard(cardId: Int) {
        deckCards = deckCards.filter { it.cardId != cardId }.toMutableList()
    }

}
