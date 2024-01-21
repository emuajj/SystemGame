package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    TASK,
    CPU,
    GENERIC
}

interface ICardGeneric {
    var cardId: Int?
    val title: String
    val type: CardType
    val description: String
    val name: String
}

interface ITaskCard : ICardGeneric {
    val cueTime: Int
    val cpuCycles: Int
}

interface ICpuCard : ICardGeneric {
    val clockSpeed: Int
}

data class TaskCard(
    override var cardId : Int?,
    override val title: String,
    override val type: CardType = CardType.TASK,
    override val description: String,
    override val name: String,
    override val cueTime: Int,
    override val cpuCycles: Int,
) : ITaskCard

data class CpuCard(
    override var cardId : Int?,
    override val title: String,
    override val type: CardType = CardType.CPU,
    override val description: String,
    override val name: String,
    override val clockSpeed: Int,
) : ICpuCard