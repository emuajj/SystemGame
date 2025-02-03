package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joanjaume.myapplication.android.Repository.CardProviders
import com.joanjaume.myapplication.models.cards.cpuCard.cpu
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.repository.CardProvider
import com.joanjaume.myapplication.repository.TypesProvider

class SimulationViewModel(private val userId: String) : ViewModel() {
    // LiveData Initialization with default values
    private val _stepperStep = MutableLiveData<Int>(1)
    val stepperStep: LiveData<Int> = _stepperStep

    private val _taskCards = MutableLiveData<List<TaskCard>>(emptyList())
    val taskCards: LiveData<List<TaskCard>> = _taskCards

    private val _selectedTaskCards = MutableLiveData<List<TaskCard>>(emptyList())
    val selectedTaskCards: LiveData<List<TaskCard>> = _selectedTaskCards

    private val _algorithmCards = MutableLiveData<List<AlgorithmCard>>(emptyList())
    val algorithmCards: LiveData<List<AlgorithmCard>> = _algorithmCards

    private val _selectedAlgorithmCard = MutableLiveData<AlgorithmCard?>()
    val selectedAlgorithmCard: LiveData<AlgorithmCard?> = _selectedAlgorithmCard

    private val _cpuCards = MutableLiveData<List<CpuCard>>(emptyList())
    val cpuCards: LiveData<List<CpuCard>> = _cpuCards

    private val _selectedCpuCard = MutableLiveData<CpuCard?>()
    val selectedCpuCard: LiveData<CpuCard?> = _selectedCpuCard

    private val cardProvider = CardProvider()

    private val cardProviders = CardProviders()

    private val typeProvider = TypesProvider()

    fun initializeStepOne() {

        cardProviders.getUserTaskCards(userId) { taskCards, error ->
            if (error != null) {
                // Log the error or handle it as needed
                println("Error retrieving task cards: ${error.message}")
                // Optionally update a LiveData or state to reflect the error
            } else if (taskCards != null) {
                // Update your LiveData or State with the retrieved cards
                _taskCards.value = taskCards
            } else {
                // Handle the unlikely case where taskCards is null and there's no error
                println("No task cards found and no error reported.")
            }
        }


    }


    fun initializeStepTwo() {
//        _algorithmCards.value = cardProvider.getAllAlgorithmCards()
        cardProviders.getUserAlgorithmCards(userId) { algorithmCards, error ->
            if (error != null) {
                // Log the error or handle it as needed
                println("Error retrieving task cards: ${error.message}")
                // Optionally update a LiveData or state to reflect the erro
            } else if (algorithmCards != null) {
                // Update your LiveData with the retrieved cards
                _algorithmCards.value = algorithmCards
            } else
            // Handle the case where taskCards is null and there's no error (unlikely but possible)
                println("No task cards found and no error reported.")
        }
    }

    fun initializeStepThree() {
//        _cpuCards.value = cardProvider.getAllCpuCards()
        cardProviders.getUserCpuCards(userId) { cpuCards, error ->
            if (error != null) {
                // Log the error or handle it as needed
                println("Error retrieving task cards: ${error.message}")
                // Optionally update a LiveData or state to reflect the erro
            } else if (cpuCards != null) {
                // Update your LiveData with the retrieved cards
                _cpuCards.value = cpuCards
            } else
            // Handle the case where taskCards is null and there's no error (unlikely but possible)
                println("No task cards found and no error reported.")
        }
    }

    fun handleClickCardStepper(card: ICardGeneric) {
        when (card.type) {
            CardType.TASK -> {
                _selectedTaskCards.value = _selectedTaskCards.value!! + (card as TaskCard)
            }
            CardType.ALGORITHM -> {
                _selectedAlgorithmCard.value = card as AlgorithmCard
            }
            CardType.CPU -> {
                _selectedCpuCard.value = card as CpuCard
            }
            else -> {}
        }
    }

    fun onStepperValueChange(newVal: Int) {
        _stepperStep.value = newVal
    }

    fun getAlgorithmOptions(): Map<String, Int> {
        return typeProvider.getCardAlgorithms()
    }

    fun getModalityOptions(): Map<String, Int> {
        return typeProvider.getCardModalities()
    }

    fun getAlgorithmByName(name: String): Int {
        return typeProvider.getAlgorithmByName(name)
    }


    fun saveNewCard(card: ICardGeneric) {
        when (card.type) {
            CardType.TASK -> {
                cardProviders.addUserTaskCard(card as TaskCard, userId) { success, error ->
                    if (success) {
                        // Handle success (e.g., notify the user, update UI)
                        println("TaskCard successfully added!")
                    } else {
                        // Log the error or handle it as needed
                        println("Failed to add TaskCard: ${error?.message}")
                        // Optionally update a LiveData or state to reflect the error
                    }
                }
                initializeStepOne()
            }
            CardType.ALGORITHM -> {
//                cardProvider.handleAddAlgorithmCard(card as AlgorithmCard)
                cardProviders.addUserAlgorithmCard(
                    card as AlgorithmCard,
                    userId
                ) { success, error ->
                    if (success) {
                        // Handle success (e.g., notify the user, update UI)
                        println("TaskCard successfully added!")
                    } else {
                        // Log the error or handle it as needed
                        println("Failed to add TaskCard: ${error?.message}")
                        // Optionally update a LiveData or state to reflect the error
                    }
                }
                initializeStepTwo()
            }
            CardType.CPU -> {
                cardProviders.addUserCpuCard(card as CpuCard, userId) { success, error ->
                    if (success) {
                        // Handle success (e.g., notify the user, update UI)
                        println("TaskCard successfully added!")
                    } else {
                        // Log the error or handle it as needed
                        println("Failed to add TaskCard: ${error?.message}")
                        // Optionally update a LiveData or state to reflect the error
                    }
                }
                initializeStepThree()
            }
            else -> {}
        }
    }
}
