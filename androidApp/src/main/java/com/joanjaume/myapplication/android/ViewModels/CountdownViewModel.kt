package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.gameModels.CountdownData
import com.joanjaume.myapplication.models.interfaces.cardInterface.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountDownUiState(
    var deck: List<ICardGeneric> = emptyList(),
    var ganttTasks: Map<Int, TaskCard> = emptyMap(),
    var cpuCard: CpuCard? = null,
    var countdownSeconds: Int = 1000,
    var timeCount: Long = 0,
)


class CountdownViewModel(private val countdownData: CountdownData) : ViewModel() {


    //    private val countdownData = MutableLiveData<CountdownData>()
    private val _countDownUiState = MutableStateFlow(CountDownUiState())
    val countDownUiState: StateFlow<CountDownUiState> = _countDownUiState

    private var countdownJob: Job? = null
    private var countdownFinishedCallback: (() -> Unit)? = null

    init {
//        initCountDown()
//        viewModelScope.launch {
//            _countDownUiState.value.cpuCard = countdownData.getCpuCard().first()
//            _countDownUiState.value.ganttTasks = countdownData.getGantt()
//        }
        startCountdown(200, ::handleTimeFinished)
    }

//    private fun initCountDown() {
//        countdownData.initCountdown()
//        setDeck()
//    }


//    private fun setGantt(iteration: Long?) {
//
//        viewModelScope.launch {
//            _countDownUiState.value =
//                _countDownUiState.value.copy(ganttTasks = countdownData.getGantt())
//            if (iteration !== null) {
//                _countDownUiState.value.timeCount = iteration
//
//            }
//        }
//    }

//    private fun setDeck() {
//        viewModelScope.launch {
//            _countDownUiState.value = _countDownUiState.value.copy(deck = countdownData.getDeck())
//        }
//    }
//
//    private fun setCpuCard() {
//        viewModelScope.launch {
//            _countDownUiState.value.cpuCard = countdownData.getCpuCard().first()
//        }
//    }


    private fun updateGantt() {

    }

//    private fun setGantt(iteration: Long?) {
//
//        viewModelScope.launch {
//            _countDownUiState.value =
//                _countDownUiState.value.copy(ganttTasks = countdownData.getGantt())
//            if (iteration !== null) {
//                _countDownUiState.value.timeCount = iteration
//
//            }
//        }
//    }


//    private fun getGantt(): Map<Int, GanttTask> {
//        return _countDownUiState.value.ganttTasks
//    }
//
//    private fun getTasks() {
//
//    }

//    private fun setGantt(iteration: Long?) {
//
//        viewModelScope.launch {
//            _countDownUiState.value =
//                _countDownUiState.value.copy(ganttTasks = countdownData.getGantt())
//            if (iteration !== null) {
//                _countDownUiState.value.timeCount = iteration
//
//            }
//        }
//    }

//    private fun setGantt(iteration: Long?) {
//
//        viewModelScope.launch {
//            _countDownUiState.value =
//                _countDownUiState.value.copy(ganttTasks = countdownData.getGantt())
//            if (iteration !== null) {
//                _countDownUiState.value.timeCount = iteration
//
//            }
//        }
//    }


//    fun onUserClick(card) {
//        val taskCard = TaskCard(
//            id = generateId(),
//            name = name,
//            arriveTime = arriveTime,
//            burst = burst,
//            priority = priority,
//            description = "A user-created task"
//        )
//        countdownData.addUserProcess(taskCard)
//    }

//    fun runNextSchedulerStep(algorithm: Int, modality: Int) {
//        countdownData.runNextSchedulerStep(algorithm, modality)
//    }

//    fun getGanttChart(): String {
//        return countdownData.getGanttChart()
//    }

    // Deck management methods
    fun addCardToDeck(card: TaskCard) {
        countdownData.addCardToDeck(card)
    }

    fun removeCardFromDeck(card: TaskCard) {
        countdownData.removeCardFromDeck(card)
    }

//    fun getDeckCards(): List<TaskCard> {
//        return countdownData.getDeckCards()
//    }
//
//    fun playCard(card: TaskCard) {
//        countdownData.playCard(card)
//    }

//    fun handleClickCard(card: ICardGeneric) {
//        when (card.type) {
//            CardType.CPU -> {
//                if (card is TaskCard.CpuCard) {
//                    // OPEN MODAL TO SEE COMPLETE CPU DETAILS
//                    // Handle CPU card click logic here
//                    // For now, we'll just log the card details
//                    println("CPU Card clicked: $card")
//                }
//            }
//            CardType.TASK -> {
//                if (card is TaskCard) {
//                    countdownData.playCard(card)
//                    // Assuming countdownData and setDeck, setGantt are defined elsewhere in your code
//                    countdownData.removeOneCardFromDeck(card.id)
//                    countdownData.onTaskCardClicked(card)
//                    setDeck()
//                    setGantt(null)
//                }
//            }
//            else -> {
//                // Handle default case or unknown card types
//                println("Unknown card type clicked: $card")
//            }
//        }
//    }

    private fun generateId(): Int {
        // Generate a unique ID for a new task card
        return (Math.random() * 10000).toInt()
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
        }

        //ADDITIONAL THINGS
    }
}