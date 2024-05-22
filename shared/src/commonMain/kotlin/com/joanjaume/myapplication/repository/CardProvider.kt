package com.joanjaume.myapplication.repository


import com.joanjaume.myapplication.models.interfaces.cardInterface.*

class CardProvider {

    fun getInitialTaskCards(): List<ICardGeneric> {
        return predefinedTaskCards
    }

    fun getCpuCard(): CpuCard {
        return predefinedCpuCard
    }

    fun getAlgorithmCard(): AlgorithmCard {
        return predefinedAlgorithmCard
    }

}


private val predefinedTaskCards: List<ICardGeneric> = listOf(
    TaskCard(null, "1--TASKCARD", CardType.TASK, "Description for Card 1", 0, 1, 10),
    TaskCard(null, "2--TASKCARD", CardType.TASK, "Description for Card 2", 0, 2, 1),
    TaskCard(null, "3--TASKCARD", CardType.TASK, "Description for Card 3", 0, 3, 1),
    CpuCard(null, "SubaDubaSLOW", CardType.CPU, "SubaDubaSLOW", 1),
    AlgorithmCard(null, "AlgorithmFAST", "", CardType.ALGORITHM, 2, 2)
    // Add more predefined cards as needed
)

private val predefinedCpuCard: CpuCard =
    CpuCard(null, "SubaDubaFAST", CardType.CPU, "SubaDubaFAST", 3)

private val predefinedAlgorithmCard: AlgorithmCard =
    AlgorithmCard(null, "AlgorithmSLOW", "", CardType.ALGORITHM, 1, 1)
