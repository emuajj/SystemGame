package com.joanjaume.myapplication.android.`view-models`

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

data class ModelUiState(
    var deck: List<ICardGeneric> = emptyList(),
    var ganttTasks: List<TaskCard> = emptyList(),
    var cpuCard: CpuCard? = null,
    var algorithmCard: AlgorithmCard? = null,
    var countdownSeconds: Int = 1000,
    var timeCount: Int = 0,
)

class ViewModel(private val model: Model) : ViewModel() {

    private val _modelUiState = MutableStateFlow(ModelUiState())
    val modelUiState: StateFlow<ModelUiState> = _modelUiState

    init {
        model.initModel()
        updateProcessQueue()
        updateDeck()
        updateCpuCard()
        updateAlgorithmCard()
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
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.copy(deck = model.getDeck())
        }
    }

    private fun updateCpuCard() {
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.copy(cpuCard = model.getCpuCard())
        }
    }

    private fun updateAlgorithmCard() {
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.copy(algorithmCard = model.getAlgorithmCard())
        }
    }

    private fun updateCurrentTime() {
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.copy(timeCount = model.getCurrentTime())
        }
    }

    private fun updateProcessQueue() {
        viewModelScope.launch {
            _modelUiState.value = _modelUiState.value.copy(ganttTasks = model.getProcessTable())
            println("processQueue ${model.getProcessTable()}")
        }
    }
}