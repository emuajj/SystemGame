package com.joanjaume.myapplication.models.gameModels

import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.repository.CardProvider

class Model {
    var countdownData = CountdownData()
    var cardProvider = CardProvider()

    fun initModel() {
        countdownData.addCardsToDeck(cardProvider.getInitialTaskCards())
    }

    fun handleClickCard(card: ICardGeneric) {
        if (card.type == CardType.TASK) {
//            countdownData.addCardToDeck(card as TaskCard)
            // DO THING
            countdownData.addUserProcess(card as TaskCard)
        }
    }


    fun getDeck(): List<ICardGeneric> {
        return countdownData.getDeckCards()
    }

    fun getProcessTable(): List<TaskCard> {
        return countdownData.getProcessTable()
    }

    fun getCurrentTime(): Int {
        return countdownData.getCurrentTime()
    }


}