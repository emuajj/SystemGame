package com.joanjaume.myapplication.models.cards.`card-generic`

open class CardGeneric(
    val title: String,
    val type: String,
    val description: String,
    val name: String
) {
    override fun toString(): String {
        return "Title: $title\nType: $type\nDescription: $description\nName: $name"
    }
}