package com.joanjaume.myapplication.android.`view-models`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.gameModels.CountdownData
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountDownUiState(
    var deck: List<ICardGeneric> = emptyList(),
    var cpuCard : CpuCard? = null,
)


class CountdownViewModel(private val countdownData: CountdownData) : ViewModel() {


    //    private val countdownData = MutableLiveData<CountdownData>()
    private val _countDownUiState = MutableStateFlow(CountDownUiState())
    val countDownUiState: StateFlow<CountDownUiState> = _countDownUiState

    init {
        initCountDown()
        _countDownUiState.value.cpuCard = countdownData.getCpuCard().first()
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

    private fun getTasks() {

    }
}