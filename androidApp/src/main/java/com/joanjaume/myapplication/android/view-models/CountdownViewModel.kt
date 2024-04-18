package com.joanjaume.myapplication.android.`view-models`

import GanttChart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.gameModels.CountdownData
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountDownUiState(
    var deck: List<ICardGeneric> = emptyList(),
    var ganttTasks: Map<Int, GanttTask> = emptyMap(),
    var cpuCard: CpuCard? = null,
    var countdownSeconds: Int = 10
)


class CountdownViewModel(private val countdownData: CountdownData) : ViewModel() {


    //    private val countdownData = MutableLiveData<CountdownData>()
    private val _countDownUiState = MutableStateFlow(CountDownUiState())
    val countDownUiState: StateFlow<CountDownUiState> = _countDownUiState

    private var countdownJob: Job? = null
    private var countdownFinishedCallback: (() -> Unit)? = null

    init {
        initCountDown()
        startCountdown(20, ::handleTimeFinished)
        viewModelScope.launch {
            _countDownUiState.value.cpuCard = countdownData.getCpuCard().first()
            _countDownUiState.value.ganttTasks = countdownData.getGantt()
        }
    }

    private fun initCountDown() {
        countdownData.initCountdown()
        setDeck()
    }


    fun addCard() {
        countdownData.addCard()
        setDeck()
    }

    private fun setDeck() {
        viewModelScope.launch {
            _countDownUiState.value = _countDownUiState.value.copy(deck = countdownData.getDeck())
        }
    }


    private fun updateGantt(){

    }
    private fun setGantt() {
        viewModelScope.launch {
            _countDownUiState.value = _countDownUiState.value.copy(ganttTasks = countdownData.getGantt())
        }
    }



    private fun getGantt(): Map<Int, GanttTask> {
        return _countDownUiState.value.ganttTasks
    }

    private fun getTasks() {

    }

    fun handleClickCard(card: ICardGeneric) {
        card.type.let { cardType ->
            when (cardType) {
                CardType.CPU -> {
                    // OPEN MODAL TO SEE COMPLETE CPU DETAILS
                    if (card is ICpuCard) {
                        viewModelScope.launch {
                            _countDownUiState.value.cpuCard = card as CpuCard
                        }
                    }
                }
                CardType.TASK -> {
                    if (card is TaskCard) {
                        println("Type 2 card clicked: $card")
                        card.cardId?.let { cardId ->
                            countdownData.removeOneCardFromDeck(cardId)
                            countdownData.addCardToGantt(card)
                            setDeck()
                            setGantt()
                        }
                    }
                }
                // Add more cases as needed for other card types
                else -> {
                    // Handle default case or unknown card types
                    println("Unknown card type clicked: $card")
                }
            }
        }
    }


    private fun startCountdown(seconds: Int, onFinish: () -> Unit) {
        countdownFinishedCallback = onFinish
        countdownJob = viewModelScope.launch {
            _countDownUiState.value = _countDownUiState.value.copy(countdownSeconds = seconds)
            while (_countDownUiState.value.countdownSeconds > 0) {
                delay(1000)
                _countDownUiState.value =
                    _countDownUiState.value.copy(countdownSeconds = _countDownUiState.value.countdownSeconds - 1)
            }
            countdownFinishedCallback?.invoke()
        }
    }

    fun handleTimeFinished() {
        var taskCard: ICardGeneric? = null

        for (card in _countDownUiState.value.deck) {
            if (card.type == CardType.TASK) {
                taskCard = card
                break
            }
        }

        taskCard?.let { task ->
            val updatedDeck = _countDownUiState.value.deck.toMutableList()
            updatedDeck.remove(task)

            viewModelScope.launch {
                _countDownUiState.value = _countDownUiState.value.copy(deck = updatedDeck)
            }
            handleClickCard(task)
        }

        //ADDITIONAL THINGS
    }
}