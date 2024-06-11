package com.joanjaume.myapplication.android.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
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

    private val _timeCount = MutableLiveData<Int>()
    val timeCount: LiveData<Int> = _timeCount

    private val processQueue = ProcessQueue()
    private val scheduler = Scheduler(processQueue)
    private var processIdCounter = 0
    private var isActiveGantt = true


    init {
        for (taskcard in taskCards) {
            val newProcess = taskcard.copy(id = processIdCounter++)
            newProcess.arriveTime = scheduler.currentTime
            newProcess.state = TaskCard.New
            scheduler.addProcess(newProcess)
        }
        startScheduler()

    }


    fun startScheduler() {
        viewModelScope.launch {
            while (isActiveGantt) {  // isActive checks if the coroutine is still active
                runNextSchedulerStep()
                delay(1000L)
            }
        }
    }

    fun runNextSchedulerStep() {
        scheduler.runNextStep(
            algorithm = algorithmCard.algorithm,
            modality = algorithmCard.modality,
            quantum = 1
        )
        _timeCount.value = scheduler.currentTime
        _ganttTasks.value = scheduler.getProcessTable()
        if (processQueue.size() == 0) {
            isActiveGantt = false
        }
    }
//    fun runNextSchedulerStep() {
//        // Handle I/O for tasks
//        scheduler.getProcessTable().forEach { task ->
//            if (task.ioRequired && task.ioDuration > 0) {
//                scheduler.requestIO(task)
//            } else if (task.state == TaskCard.WaitingForIO && task.ioDuration <= 0) {
//                scheduler.completeIO(task)
//            }
//        }
//
//        scheduler.runNextStep(
//            algorithm = algorithmCard.algorithm,
//            modality = algorithmCard.modality,
//            quantum = algorithmCard.quantum ?: 0
//        )
//        _timeCount.value = scheduler.currentTime
//        _ganttTasks.value = scheduler.getProcessTable()
//        if (processQueue.size() == 0 && scheduler.getProcessTable().none { it.ioRequired }) {
//            isActiveGantt = false
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        // Coroutine scope will automatically cancel when ViewModel is cleared.
    }

}