package com.joanjaume.myapplication.models.gameModels

import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.repository.CardProvider

class Model() {
    var countdownData = CountdownData()
    var cardProvider = CardProvider()

    fun initModel() {
        countdownData.addCardsToDeck(cardProvider.getInitialTaskCards())
        countdownData.setCpuCard(cardProvider.getCpuCard())
        countdownData.setAlgorithmCard(cardProvider.getAlgorithmCard())
    }

    fun handleClickCard(card: ICardGeneric) {
        if (card.type == CardType.TASK) {
            // DO THING
            countdownData.addUserProcess(card as TaskCard)
            countdownData.removeCardFromDeck(card)
        } else {
            countdownData.handleGenericClick(card)
            countdownData.removeCardFromDeck(card)
        }
    }


    fun getDeck(): List<ICardGeneric> {
        return countdownData.getDeckCards()
    }

    fun getCpuCard(): CpuCard? {
        return countdownData.getCpuCard()
    }

    fun getAlgorithmCard(): AlgorithmCard? {
        return countdownData.getAlgorithmCard()
    }

    fun getProcessTable(): List<TaskCard> {
        return countdownData.getProcessTable()
    }

    fun getCurrentTime(): Int {
        return countdownData.getCurrentTime()
    }


}