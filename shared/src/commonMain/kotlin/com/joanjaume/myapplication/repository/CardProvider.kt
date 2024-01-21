package com.joanjaume.myapplication.repository


import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class CardProvider {

    fun getInitialTaskCards(): List<ICardGeneric> {
        return predefinedTaskCards
    }

    fun getCpuCard(): ICardGeneric {
        return predefinedCpuCard
    }

}

private val predefinedTaskCards: List<ICardGeneric> = listOf(
    TaskCard("Card 1", CardType.CPU, "Description for Card 1", "not task", 4, 2),
    TaskCard("Card 2", CardType.TASK, "Description for Card 2", "TASK", 4, 2),
    TaskCard("Card 2", CardType.CPU, "Description for Card 2", "not task", 4, 2)
    // Add more predefined cards as needed
)

private val predefinedCpuCard: ICardGeneric =
    CpuCard("CpuCard", CardType.CPU, "CpuCard", "CPU 1", 1)
