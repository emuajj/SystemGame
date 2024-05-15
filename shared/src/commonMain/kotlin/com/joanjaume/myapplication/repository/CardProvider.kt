package com.joanjaume.myapplication.repository


import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

class CardProvider {

    fun getInitialTaskCards(): List<ICardGeneric> {
        return predefinedTaskCards
    }

    fun getCpuCard(): CpuCard {
        return predefinedCpuCard
    }

}


private val predefinedTaskCards: List<ICardGeneric> = listOf(
    TaskCard(null, "Card 1", CardType.TASK, "Description for Card 1", "TASK", 4, 1),
    TaskCard(null, "Card 2", CardType.TASK, "Description for Card 2", "TASK", 4, 2),
    TaskCard(null, "Card 2", CardType.TASK, "Description for Card 2", "TASK", 4, 3),
    CpuCard(null, "CPU CARD", CardType.CPU, "Descripcion for Cpu", "CARD", clockSpeed = 1)
    // Add more predefined cards as needed
)

private val predefinedCpuCard: CpuCard =
    CpuCard(null, "CpuCard", CardType.CPU, "CpuCard", "CPU 3", 1)
