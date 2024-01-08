package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    TASK
}

interface ICardGeneric {
    val title: String
    val type: String
    val description: String
    val name: String
}

data class CardGeneric(
    override val title: String,
    override val type: String,
    override val description: String,
    override val name: String
) : ICardGeneric