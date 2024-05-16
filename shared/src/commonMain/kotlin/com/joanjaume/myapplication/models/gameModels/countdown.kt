package com.joanjaume.myapplication.models.gameModels

import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.scheduler.Scheduler
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import com.joanjaume.myapplication.repository.CardProvider


class CountdownData {
    private val processQueue = ProcessQueue()
    private val scheduler = Scheduler(processQueue)
    private val deck: Deck = Deck()
    private var processIdCounter = 0

    fun addUserProcess(card: TaskCard) {
        val newProcess = card.copy(id = processIdCounter++)
        scheduler.addProcess(newProcess)
        scheduler.runNextStep(1, 1)
        deck.removeCard(card)
    }

    fun runNextSchedulerStep(algorithm: Int, modality: Int) {
        scheduler.runNextStep(algorithm, modality)
    }

    fun getGanttChart(): String {
        return scheduler.updateGantt()
    }

    // Deck management methods
    fun addCardToDeck(card: TaskCard) {
        deck.addCard(card)
    }

    fun removeCardFromDeck(card: TaskCard) {
        deck.removeCard(card)
    }

    fun getDeckCards(): List<ICardGeneric> {
        return deck.getCards()
    }

    fun addCardsToDeck(cards: List<ICardGeneric>) {
        cards.forEach {
            deck.addCard(it)
        }
    }

    fun getProcessTable(): List<TaskCard> {
        return scheduler.getProcessTable();
    }

    fun getCurrentTime(): Int {
        return scheduler.currentTime
    }

    fun iterateTime() {
        scheduler.runNextStep(1, 1)
    }

//    fun playCard(card: TaskCard) {
//        removeCardFromDeck(card)
//        addUserProcess(card)
//    }
}