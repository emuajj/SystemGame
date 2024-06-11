package com.joanjaume.myapplication.repository


import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import kotlin.math.max

class CardProvider {

    fun getRandomCards(numberOfCards: Int): List<ICardGeneric> {
        var adjustedNumberOfCards = max(3, numberOfCards);

        val randomCards = mutableListOf<ICardGeneric>()

        // Ensure that there are enough cards to fulfill the requirements
        if (adjustedNumberOfCards < 3 || cpuTable.isEmpty() || algorithmTable.isEmpty() || taskTable.isEmpty()) {
            throw IllegalArgumentException("Not enough cards to fulfill the requirements.")
        }

        // Add one random CpuCard
        randomCards += cpuTable.random()

        // Add one random AlgorithmCard
        randomCards += algorithmTable.random()

        // Fill the rest of the list with TaskCards
        val remainingCards = adjustedNumberOfCards - 2
        val shuffledTaskCards = taskTable.shuffled()
        if (shuffledTaskCards.size < remainingCards) {
            throw IllegalArgumentException("Not enough task cards to fulfill the requirements.")
        }

        randomCards += shuffledTaskCards.take(remainingCards)

        return randomCards
    }

    fun getCpuCard(): CpuCard {
        var randomElement = cpuTable.random()
        return randomElement
    }

    fun getAlgorithmCard(): AlgorithmCard {
        return algorithmTable.random()
    }

    fun getAllTaskCards(): List<TaskCard> {
        return taskTable
    }

    fun getAllAlgorithmCards(): List<AlgorithmCard> {
        return algorithmTable
    }

    fun getAllCpuCards(): List<CpuCard> {
        return cpuTable
    }

    fun handleAddAlgorithmCard(card: AlgorithmCard) {
        algorithmTable = listOf(card) + algorithmTable
    }

    fun handleAddTaskCard(card: TaskCard) {
        taskTable = listOf(card) + taskTable
    }

    fun handleAddCpuCard(card: CpuCard) {
        cpuTable = listOf(card) + cpuTable
    }


}


//mockup
private var taskTable: List<TaskCard> = listOf(
    TaskCard(null, "1--TASKCARD", CardType.TASK, "Description for Card 1", 0, 1, 10, true, 3),
    TaskCard(null, "2--TASKCARD", CardType.TASK, "Description for Card 2", 0, 2, 1, true, 2),
    TaskCard(null, "3--TASKCARD", CardType.TASK, "Description for Card 3", 0, 3, 12),
    TaskCard(null, "4--TASKCARD", CardType.TASK, "Description for Card 4", 0, 3, 16),
    TaskCard(null, "5--TASKCARD", CardType.TASK, "Description for Card 5", 0, 3, 4),
    TaskCard(null, "6--TASKCARD", CardType.TASK, "Description for Card 6", 0, 3, 25),
    TaskCard(null, "7--TASKCARD", CardType.TASK, "Description for Card 7", 0, 3, 6),
    TaskCard(null, "8--TASKCARD", CardType.TASK, "Description for Card 8", 0, 3, 3),
    TaskCard(null, "9--TASKCARD", CardType.TASK, "Description for Card 9", 0, 3, 1),
    TaskCard(null, "10--TASKCARD", CardType.TASK, "Description for Card 10", 0, 3, 12),

    )

private var cpuTable: List<CpuCard> = listOf(
    CpuCard(null, "SubaDubaFAST", CardType.CPU, "SubaDubaFAST", 3),
    CpuCard(null, "SubaDubaSLOW", CardType.CPU, "SubaDubaSLOW", 1),


    )

private var algorithmTable: List<AlgorithmCard> = listOf(
    AlgorithmCard(null, "AlgorithmSLOW", "", CardType.ALGORITHM, 1, 1),
    AlgorithmCard(null, "AlgorithmFAST", "", CardType.ALGORITHM, 2, 2)

)
