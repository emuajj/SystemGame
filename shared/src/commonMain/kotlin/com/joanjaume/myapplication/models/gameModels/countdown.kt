package com.joanjaume.myapplication.models.gameModels

import GanttChart
import com.joanjaume.myapplication.models.gameBoard.GameBoard
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import com.joanjaume.myapplication.repository.CardProvider


class CountdownData(var difficulty: String) {
    private var cardProvider: CardProvider = CardProvider();
    private lateinit var gameBoard: GameBoard;

    fun initCountdown() {
        gameBoard = GameBoard()
        initDeck()
        initCpu()
    }

    fun getDeck(): List<ICardGeneric> {
        return gameBoard.getDeck()
    }

    fun addCard() {
        gameBoard.addCardToDeck()
    }

    private fun initDeck() {
        gameBoard.setDeck(cardProvider.getInitialTaskCards())
        print("tasks ${cardProvider.getInitialTaskCards()}")
    }

    private fun initCpu() {
        gameBoard.handleChangeCpus(listOf(cardProvider.getCpuCard()))

    }

    fun getCpuCard(): List<CpuCard> {
        return gameBoard.getCpus()
    }

    fun setCpuCard(cards: List<CpuCard>) {
        return gameBoard.handleChangeCpus(cards)
    }

    fun setGantt(gant: GanttChart) {
        gameBoard.setGantt(gant)
    }

    fun getGantt(): Map<Int, GanttTask> {
        return gameBoard.getGanttChart()  // Directly return the Gantt chart map from the GameBoard
    }

    fun iterateTime(): Long {
        return gameBoard.iterateTime()
    }


    fun removeOneCardFromDeck(cardId: Int) {
        gameBoard.removeOneCardFromDeck(cardId)
    }


    fun addCardToGantt(card: TaskCard) {
        gameBoard.onTaskCardClicked(card)  // Delegate the task addition to the GameBoard
    }

    fun onTaskCardClicked(card: TaskCard) {
        gameBoard.onTaskCardClicked(card)
    }


}