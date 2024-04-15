package com.joanjaume.myapplication.models.gameModels

import GanttChart
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.gameBoard.GameBoard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import com.joanjaume.myapplication.repository.CardProvider

class CountdownData(var difficulty: String) {
    private var deck = Deck()
    private var cardProvider: CardProvider = CardProvider();
    private lateinit var gameBoard: GameBoard

    fun initCountdown(){
        initDeck()
        initCpuCard()
        gameBoard = GameBoard(deck)
    }

    fun getDeck(): List<ICardGeneric> {
        return deck.getDeck()
    }

    fun addCard() {
        deck.addCard(
            TaskCard(
                null,
                "Card 1",
                CardType.TASK,
                "Description for Card 1",
                "John Doe",
                5,
                3
            )
        )
    }

    fun initDeck() {
        deck.setDeck(cardProvider.getInitialTaskCards())
    }

    private fun initCpuCard () {
        deck.addCard(cardProvider.getCpuCard())
    }

    fun getCpuCard() : MutableList<CpuCard> {
        return gameBoard.actualCpus
    }

    fun setGantt(gant : GanttChart){
        gameBoard.setGantt(gant)
    }

    fun getGantt() : Map<Int, GanttTask>{
        return gameBoard.ganttChart.getGanttChartt()
    }

}