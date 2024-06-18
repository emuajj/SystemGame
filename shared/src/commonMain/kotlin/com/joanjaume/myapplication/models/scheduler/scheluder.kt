package com.joanjaume.myapplication.models.scheduler

import com.joanjaume.myapplication.models.interfaces.cardInterface.Algorithm
import com.joanjaume.myapplication.models.interfaces.cardInterface.Modality
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.Results
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import kotlin.jvm.JvmName


class Scheduler(private val processQueue: ProcessQueue) {
    private val processTable = mutableListOf<TaskCard>()
    var currentTime: Int = 0
    var selected: TaskCard? = null
    private var timeSliceRemaining: Int = 0

    private val _processTable: List<TaskCard>
        @JvmName("getProcessTableProperty") get() = processTable.toList()

    fun addProcess(process: TaskCard) {
        process.lifecycle = MutableList(currentTime + 1) { -1 }
        processTable.add(process)
        processQueue.enqueue(process)
        process.state = TaskCard.New
    }

    fun updateGantt(): String {
        val ganttChart = StringBuilder()
        processTable.forEach { process ->
            ganttChart.append("|${process.name} ${process.lifecycle.joinToString("") { it.toString() }}|\n")
        }
        return ganttChart.toString()
    }

    fun runNextStep(algorithm: Int, modality: Int, quantum: Int) {
        // Enqueue arriving processes
        processTable.forEach { process ->
            if (process.arriveTime == currentTime && process.state == TaskCard.Ready) {
                process.lifecycle.add(TaskCard.Blocked)
                processQueue.enqueue(process)
            }
        }

        when (algorithm) {
            Algorithm.PRIORITIES -> {
                handlePriorities()
            }
            Algorithm.ROUND_ROBIN -> {
                handleRoundRobin(quantum)
            }
            Algorithm.SJF -> {
                handleSJF()
            }
            Algorithm.HRRN -> {
                handleHRRN()
            }
        }

        if (modality == Modality.PREEMPTIVE) {
            handlePreemption(algorithm)
        }

        selected?.let { runCurrentProcess(it) }

        currentTime++
    }

    private fun handleSJF() {
        if (selected == null) {
            val shortestJob = processQueue.getList().filter { !it.completed }
                .minByOrNull { it.burst.size }
            selected = shortestJob
        }
    }

    private fun handlePriorities() {
        if (selected == null) {
            val highestPriorityJob = processQueue.getList().filter { !it.completed }
                .minByOrNull { it.priority }
            selected = highestPriorityJob
        }
    }


    private fun handleRoundRobin(quantum: Int) {
        if (selected == null || timeSliceRemaining == 0) {
            selected?.let {
                if (!it.completed) {
                    it.lifecycle.add(TaskCard.Blocked)
                    processQueue.enqueue(it)
                }
            }
            selected = processQueue.dequeue()
            timeSliceRemaining = quantum
        }
    }

    private fun handlePreemption(algorithm: Int) {
        val moreUrgentProcess = processTable.filter {
            it.arriveTime <= currentTime && !it.completed && it.state == TaskCard.Ready
        }.minByOrNull { process ->
            when (algorithm) {
                Algorithm.PRIORITIES -> process.priority
                Algorithm.SJF -> process.burst.size
                else -> Int.MAX_VALUE  // Default case should not preempt based on incorrect criteria
            }
        }

        // Preempt if there's a more urgent process that should take over
        if (moreUrgentProcess != null && (selected == null || moreUrgentProcess.hasHigherPriority(
                selected!!,
                algorithm
            ))
        ) {
            selected?.let {
                if (!it.completed) {
                    it.lifecycle.add(TaskCard.Blocked)
                    processQueue.enqueue(it)
                }
            }
            selected = moreUrgentProcess
        }
    }

    private fun TaskCard.hasHigherPriority(other: TaskCard, algorithm: Int): Boolean {
        return when (algorithm) {
            Algorithm.PRIORITIES -> this.priority < other.priority
            Algorithm.SJF -> this.burst.size < other.burst.size
            else -> false // In case of ROUND_ROBIN and HRRN, priority comparisons may not be relevant
        }
    }

    private fun handleHRRN() {
        val highestRatioProcess = processTable.filter {
            it.arriveTime <= currentTime && !it.completed && it.state == TaskCard.Ready
        }.maxByOrNull { calculateResponseRatio(it) }

        selected = highestRatioProcess
    }

    private fun calculateResponseRatio(process: TaskCard): Double {
        val waitingTime = currentTime - process.arriveTime
        return (waitingTime + process.burst.size).toDouble() / process.burst.size
    }

    private fun runCurrentProcess(currentProcess: TaskCard) {
        if (currentProcess.responseTime == null) {
            currentProcess.responseTime = currentTime - currentProcess.arriveTime
        }

        val currentBurst = getCurrentBurst(currentProcess)
        if (currentBurst < currentProcess.burst.size) {
            currentProcess.lifecycle.add(if (currentProcess.burst[currentBurst] == "cpu") TaskCard.Running else TaskCard.PerformingIO)
            currentProcess.state = TaskCard.Running
            timeSliceRemaining--
        }

        // Update lifecycle for all other processes
        processTable.forEach { process ->
            if (process != currentProcess && process.arriveTime <= currentTime && !process.completed) {
                process.lifecycle.add(TaskCard.Blocked)
                process.waitingTime++
                process.state = TaskCard.Blocked
            }
        }

        // Check if the current process is completed
        if (currentBurst + 1 >= currentProcess.burst.size) {
            currentProcess.completed = true
            currentProcess.returnTime = currentTime + 1 - currentProcess.arriveTime
            currentProcess.lifecycle.add(TaskCard.Finished)
            currentProcess.state = TaskCard.Finished
            selected = null // Reset selected process if it completes
            timeSliceRemaining = 0
        }

        // Update waiting time for all other processes
        processTable.forEach { process ->
            if (process != currentProcess && process.arriveTime <= currentTime && !process.completed) {
                process.waitingTime++
            }
        }
    }

    private fun getCurrentBurst(process: TaskCard): Int {
        return process.lifecycle.count { it == TaskCard.Running || it == TaskCard.PerformingIO }
    }

    fun getProcessTable(): List<TaskCard> {
        return _processTable.toList() // Returning a read-only view of the list
    }

    fun getMetrics(): Results {
        val completedProcesses = processTable.filter { it.completed }
        val averageWaitingTime = completedProcesses.map { it.waitingTime }.average()
        val averageResponseTime = completedProcesses.mapNotNull { it.responseTime }.average()
        val averageReturnTime = completedProcesses.mapNotNull { it.returnTime }.average()
        return Results(
            completedProcesses,
            averageWaitingTime,
            averageResponseTime,
            averageReturnTime
        )
    }
}





