package com.joanjaume.myapplication.models.gameModels

import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.models.scheduler.Scheduler
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import com.joanjaume.myapplication.repository.CardProvider


class CountdownData {
    private val processQueue = ProcessQueue()
    private val scheduler = Scheduler(processQueue)
    private val deck: Deck = Deck()
    private var cpuCard: CpuCard? = null
    private var algorithmCard: AlgorithmCard? = null
    private var processIdCounter = 0


    fun addUserProcess(card: TaskCard) {
        val newProcess = card.copy(id = processIdCounter++)
        newProcess.arriveTime = getCurrentTime()
        newProcess.state = TaskCard.New // Ensure the state is set to Ready before adding
        scheduler.addProcess(newProcess)
        // Run the next step with a specified algorithm and modality
        for (i in 0 until cpuCard!!.clockSpeed) {
            runNextSchedulerStep(algorithmCard!!.algorithm, algorithmCard!!.modality)
        }
    }

    fun runNextSchedulerStep(algorithm: Int, modality: Int) {
        scheduler.runNextStep(algorithm, modality, 1)
    }

    fun getGanttChart(): String {
        return ""
        //        return scheduler.updateGantt()
    }

    // Deck management methods
    fun addCardToDeck(card: TaskCard) {
        deck.addCard(card)
    }

    fun removeCardFromDeck(card: ICardGeneric) {
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
//        return scheduler.getProcessTable();
        return emptyList()
    }

    fun getCurrentTime(): Int {
        return scheduler.currentTime
    }

    fun iterateTime() {
        for (i in 0 until cpuCard!!.clockSpeed) {
            runNextSchedulerStep(algorithmCard!!.algorithm, algorithmCard!!.modality)
        }
    }

    fun setCpuCard(incomincCpu: CpuCard) {
        cpuCard = incomincCpu
    }

    fun getCpuCard(): CpuCard? {
        return cpuCard
    }

    fun getAlgorithmCard(): AlgorithmCard? {
        return algorithmCard
    }

    fun setAlgorithmCard(incomingAlgorithm: AlgorithmCard) {
        algorithmCard = incomingAlgorithm
    }

    fun handleGenericClick(card: ICardGeneric) {
        if (card.type == CardType.ALGORITHM) {
            setAlgorithmCard(card as AlgorithmCard)
        } else if (card.type == CardType.CPU) {
            setCpuCard(card as CpuCard)
        }
    }

//    fun playCard(card: TaskCard) {
//        removeCardFromDeck(card)
//        addUserProcess(card)
//    }
}