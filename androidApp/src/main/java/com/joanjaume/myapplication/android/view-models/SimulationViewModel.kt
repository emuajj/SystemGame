package com.joanjaume.myapplication.android.`view-models`

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.repository.CardProvider

class SimulationViewModel() : ViewModel() {
    //MutableLiveData
    val stepperStep = MutableLiveData<Int>()
    val taskCards = MutableLiveData<List<TaskCard>>()
    val selectedTaskCards = MutableLiveData<List<TaskCard>>()

    //Repository
    val cardProvider = CardProvider()


    //STEPPER
    fun initializeStepOne() {
        taskCards.value = cardProvider.getAllTaskCards()
    }

    fun handleClickCardStepper(card: ICardGeneric) {
        if (card.type == CardType.TASK) {
            //ADD TO SELECTEDS
            val currentSelectedList = selectedTaskCards.value ?: emptyList()
            val updatedSelectedList = currentSelectedList + (card as TaskCard)
            selectedTaskCards.value = updatedSelectedList

            // REMOVE FROM MAIN LIST
            val currentList = taskCards.value ?: emptyList()
            val updatedList = currentList.filter { it != card }
            taskCards.value = updatedList
            println("Task Cards Updated: $updatedList")
        }
    }

    fun onStepperValueChange(newVal: Int) {
        stepperStep.value = newVal
    }

}