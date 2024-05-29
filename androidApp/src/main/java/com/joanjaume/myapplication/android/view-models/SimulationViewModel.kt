package com.joanjaume.myapplication.android.`view-models`

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.gameModels.Model
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SimulationModelUiState(
    var deck: List<ICardGeneric> = emptyList(),
    var ganttTasks: List<TaskCard> = emptyList(),
    var cpuCard: CpuCard? = null,
    var algorithmCard: AlgorithmCard? = null,
    var countdownSeconds: Int = 1000,
    var timeCount: Int = 0,
)

class SimulationViewModel(private val model: Model) : ViewModel() {

    private val _modelUiState = MutableStateFlow(ModelUiState())
    val modelUiState: StateFlow<ModelUiState> = _modelUiState

    init {
        try {
            println("STARTING")
            model.initModel()
            updateProcessQueue()
            updateDeck()
            updateCpuCard()
            updateAlgorithmCard()
        } catch (e: Exception) {
            Log.e("ViewModelInit", "Error initializing model: ${e.message}")
        }
    }


    fun handleClickCard(card: ICardGeneric) {
        model.handleClickCard(card)
        updateDeck()
        updateCpuCard()
        updateAlgorithmCard()
        updateProcessQueue()
        updateCurrentTime()
    }


    fun addCard() {

    }


    fun iterateTime() {
        model.countdownData.iterateTime()
        updateProcessQueue()
        updateCurrentTime()
    }

    private fun updateDeck() {
        updateUiState { copy(deck = model.getDeck()) }
    }

    private fun updateCpuCard() {
        updateUiState { copy(cpuCard = model.getCpuCard()) }
    }

    private fun updateAlgorithmCard() {
        updateUiState { copy(algorithmCard = model.getAlgorithmCard()) }
    }

    private fun updateCurrentTime() {
        updateUiState { copy(timeCount = model.getCurrentTime()) }
    }

    private fun updateProcessQueue() {
        updateUiState { copy(ganttTasks = model.getProcessTable()) }
    }

    private fun updateUiState(transform: ModelUiState.() -> ModelUiState) {
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.transform()
        }
    }
}