package com.joanjaume.myapplication.models.deck

import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

interface IDeck {
    fun addCard(card: ICardGeneric)
    fun removeCard(card: ICardGeneric)
    fun getCards(): List<ICardGeneric>
}

class Deck : IDeck {
    private val cards: MutableList<ICardGeneric> = mutableListOf()

    override fun addCard(card: ICardGeneric) {
        cards.add(card)
    }

    override fun removeCard(card: ICardGeneric) {
        cards.remove(card)
    }

    override fun getCards(): List<ICardGeneric> {
        return cards.toList()
    }
}