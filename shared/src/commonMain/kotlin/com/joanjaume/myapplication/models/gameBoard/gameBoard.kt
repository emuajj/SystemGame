package com.joanjaume.myapplication.models.gameBoard

import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ITaskCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class GameBoard(deck: Deck) {
    var actualTasks: MutableList<TaskCard> = mutableListOf()
    var actualCpus: MutableList<CpuCard> = mutableListOf()

    init {
        for (card in deck.getDeck()) {
            if (card.type == CardType.TASK && card is TaskCard) {
                actualTasks.add(card)
            } else if (card.type == CardType.CPU && card is CpuCard) {
                    actualCpus.add(card)
            }
        }
    }
}