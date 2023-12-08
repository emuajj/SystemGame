package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    CPU,
    TASK
}

interface ICardGeneric {
    val title: String
    val type: CardType
    val description: String
    val name: String
}

data class CardGeneric(
    override val title: String,
    override val type: CardType,
    override val description: String,
    override val name: String
) : ICardGeneric