package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joanjaume.myapplication.models.cards.cpuCard.cpu
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.repository.CardProvider
import com.joanjaume.myapplication.repository.TypesProvider

class SimulationViewModel() : ViewModel() {
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

    private val typeProvider = TypesProvider()

    fun initializeStepOne() {
        _taskCards.value = cardProvider.getAllTaskCards()
    }

    fun initializeStepTwo() {
        _algorithmCards.value = cardProvider.getAllAlgorithmCards()
    }

    fun initializeStepThree() {
        _cpuCards.value = cardProvider.getAllCpuCards()
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
                cardProvider.handleAddTaskCard(card as TaskCard)
                initializeStepOne()
            }
            CardType.ALGORITHM -> {
                cardProvider.handleAddAlgorithmCard(card as AlgorithmCard)
                initializeStepTwo()
            }
            CardType.CPU -> {
            }
            else -> {}
        }
    }
}
