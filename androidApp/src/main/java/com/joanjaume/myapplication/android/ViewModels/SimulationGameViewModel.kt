package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.Results
import com.joanjaume.myapplication.models.scheduler.Scheduler
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class SimulationGameViewModel(
    taskCards: List<TaskCard>,
    private val cpuCard: CpuCard,  // Now stored as a property
    private val algorithmCard: AlgorithmCard  // Now stored as a property
) : ViewModel() {

    private val _ganttTasks = MutableLiveData<List<TaskCard>>()
    val ganttTasks: LiveData<List<TaskCard>> = _ganttTasks

    private val _results = MutableLiveData<Results>()
    val results: LiveData<Results> = _results

    private val _isResultsModalOpen = MutableLiveData(false)
    val isResultsModalOpen: LiveData<Boolean> = _isResultsModalOpen

    private val _timeCount = MutableLiveData<Int>()
    val timeCount: LiveData<Int> = _timeCount

    private val processQueue = ProcessQueue()
    private val scheduler = Scheduler(processQueue)
    private var processIdCounter = 0
    private var isActiveGantt = true
    private var delayTime = 1000L


    init {
        for (taskcard in taskCards) {
            val newProcess = taskcard.copy(id = processIdCounter++)
            newProcess.arriveTime = scheduler.currentTime
            newProcess.state = TaskCard.New
            scheduler.addProcess(newProcess)
        }
        startScheduler()

    }

    fun toggleResultsModal(shouldShow: Boolean) {
        _isResultsModalOpen.value = shouldShow
    }

    fun changeTime(action: String) {
        when (action) {
            "acc" -> delayTime /= 2
            "dec" -> delayTime *= 2
            else -> throw IllegalArgumentException("Invalid action: $action")
        }
    }


    fun startScheduler() {
        viewModelScope.launch {
            while (isActiveGantt) {  // isActive checks if the coroutine is still active
                runNextSchedulerStep()
                delay(delayTime)
            }
        }
    }

    private fun runNextSchedulerStep() {
        scheduler.runNextStep(
            algorithm = algorithmCard.algorithm,
            modality = algorithmCard.modality,
            quantum = algorithmCard.quantum ?: 1 ,
//            cpuCard = cpuCard
        )
        _timeCount.value = scheduler.currentTime
        _ganttTasks.value = scheduler.getProcessTable()
        if (areProccesFinished()) {
            isActiveGantt = false
            _results.value = scheduler.getMetrics()
            _isResultsModalOpen.value = true
        }
    }

    private fun areProccesFinished(): Boolean {
        val processTable = scheduler.getProcessTable()

        return processTable.all { it.state == 4 }
    }

    override fun onCleared() {
        super.onCleared()
        // Coroutine scope will automatically cancel when ViewModel is cleared.
    }

}