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
    TaskCard(id = null, name = "1--TASKCARD", type = CardType.TASK, description = "Description for Card 1", arriveTime = 0,
        burst = mutableListOf("cpu", "io", "cpu"), priority = 10, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "2--TASKCARD", type = CardType.TASK, description = "Description for Card 2", arriveTime = 0,
        burst = mutableListOf("cpu", "io" , "cpu" , "io" ), priority = 1, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "3--TASKCARD", type = CardType.TASK, description = "Description for Card 3", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" , "cpu" ), priority = 12, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "4--TASKCARD", type = CardType.TASK, description = "Description for Card 4", arriveTime = 0,
        burst = mutableListOf("io" , "cpu" , "io" , "cpu" ), priority = 16, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "5--TASKCARD", type = CardType.TASK, description = "Description for Card 5", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" ), priority = 4, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "6--TASKCARD", type = CardType.TASK, description = "Description for Card 6", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" , "cpu" , "io" ), priority = 25, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "7--TASKCARD", type = CardType.TASK, description = "Description for Card 7", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" , "cpu" ), priority = 6, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "8--TASKCARD", type = CardType.TASK, description = "Description for Card 8", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" ), priority = 3, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "9--TASKCARD", type = CardType.TASK, description = "Description for Card 9", arriveTime = 0,
        burst = mutableListOf("io" , "cpu" , "io" , "cpu" ), priority = 1, lifecycle = mutableListOf()),

    TaskCard(id = null, name = "10--TASKCARD", type = CardType.TASK, description = "Description for Card 10", arriveTime = 0,
        burst = mutableListOf("cpu" , "io" , "cpu" ), priority = 12, lifecycle = mutableListOf())
)



private var cpuTable: List<CpuCard> = listOf(
    CpuCard(null, "SubaDubaFAST", CardType.CPU, "SubaDubaFAST", 3),
    CpuCard(null, "SubaDubaSLOW", CardType.CPU, "SubaDubaSLOW", 1),


    )

private var algorithmTable: List<AlgorithmCard> = listOf(
    AlgorithmCard(null, "AlgorithmSLOW", "", CardType.ALGORITHM, 1, 1),
    AlgorithmCard(null, "AlgorithmFAST", "", CardType.ALGORITHM, 2, 2)

)
