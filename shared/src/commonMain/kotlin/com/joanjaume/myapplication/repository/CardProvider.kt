package com.joanjaume.myapplication.repository


import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class CardProvider {

    fun getInitialTaskCards(): List<ICardGeneric> {
        return predefinedTaskCards
    }

    fun getCpuCard(): TaskCard.CpuCard {
        return predefinedCpuCard
    }

}


private val predefinedTaskCards: List<ICardGeneric> = listOf(
    TaskCard(null, "Card 1", CardType.TASK, "Description for Card 1", 0, 1, 1),
    TaskCard(null, "Card 2", CardType.TASK, "Description for Card 2", 0, 2, 1),
    TaskCard(null, "Card 2", CardType.TASK, "Description for Card 2", 0, 3, 1),
    TaskCard.CpuCard(null, "CPU CARD", CardType.CPU, "Descripcion for Cpu", 1)
    // Add more predefined cards as needed
)

private val predefinedCpuCard: TaskCard.CpuCard =
    TaskCard.CpuCard(null, "CpuCard", CardType.CPU, "CpuCard", 1)
