package com.joanjaume.myapplication.models.scheduler

import com.joanjaume.myapplication.models.interfaces.cardInterface.Algorithm
import com.joanjaume.myapplication.models.interfaces.cardInterface.Modality
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.Results
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import kotlin.jvm.JvmName

//FIRST
//class Scheduler(private val processQueue: ProcessQueue) {
//    private val processTable = mutableListOf<TaskCard>()
//    var currentTime: Int = 0
//    var selected: TaskCard? = null
//
//    private val _processTable: List<TaskCard>
//        @JvmName("getProcessTableProperty") get() = processTable.toList()
//
//    fun addProcess(process: TaskCard) {
//        process.lifecycle = MutableList(currentTime + 1) { -1 }
//        processTable.add(process)
//        processQueue.enqueue(process)
//        process.state = TaskCard.New
//    }
//
//    fun updateGantt(): String {
//        val ganttChart = StringBuilder()
//        processTable.forEach { process ->
//            ganttChart.append("|${process.name} ${process.lifecycle.joinToString("") { it.toString() }}|\n")
//        }
//        return ganttChart.toString()
//    }
//
//
//    fun runNextStep(algorithm: Int, modality: Int) {
//
//        // Enqueue arriving processes
//        processTable.forEach { process ->
//            if (process.arriveTime == currentTime && process.state == TaskCard.Ready) {
//                process.lifecycle.add(TaskCard.Blocked)
//                processQueue.enqueue(process)
//            }
//        }
//
//        // Preemptive Check: If a higher priority or shorter job arrives, preempt current
//        if (modality == Modality.PREEMPTIVE && selected != null && !selected!!.completed) {
//            val moreUrgentProcess = processTable.filter {
//                it.arriveTime <= currentTime && !it.completed && it.state == TaskCard.Ready
//            }.minByOrNull { if (algorithm == Algorithm.PRIORITIES) it.priority else it.burst }
//
//            if (moreUrgentProcess != null && ((algorithm == Algorithm.PRIORITIES && moreUrgentProcess.priority < selected!!.priority) ||
//                        (algorithm == Algorithm.SJF && moreUrgentProcess.burst < selected!!.burst))
//            ) {
//                processQueue.enqueue(selected!!)
//                selected!!.lifecycle.add(TaskCard.Blocked)
//                selected = null
//            }
//        }
//
//        // Select the next process to run if none selected
//        if (selected == null && processQueue.size() > 0) {
//            if (algorithm == Algorithm.PRIORITIES || algorithm == Algorithm.SJF) {
//                val sortedList = if (algorithm == Algorithm.PRIORITIES) {
//                    processQueue.getList().sortedBy { it.priority }  // Ensure lower number means higher priority
//                } else {
//                    processQueue.getList().sortedBy { it.burst }
//                }
//                processQueue.setList(sortedList)
//            }
//            selected = processQueue.dequeue()
//        }
//
//        // Run the selected process for one time unit
//        selected?.let { currentProcess ->
//            val currentBurst = getCurrentBurst(currentProcess)
//            if (currentBurst < currentProcess.burst) {
//                currentProcess.lifecycle.add(TaskCard.Running)
//                currentProcess.state = TaskCard.Running
//            }
//
//            // Update lifecycle for all other processes
//            processTable.forEach { process ->
//                if (process != currentProcess && process.arriveTime <= currentTime && !process.completed) {
//                    process.lifecycle.add(TaskCard.Blocked)
//                    process.waitingTime++
//                    process.state = TaskCard.Blocked
//                }
//            }
//
//            // Check if the current process is completed
//            if (currentBurst + 1 >= currentProcess.burst) {
//                currentProcess.completed = true
//                currentProcess.returnTime = currentTime + 1 - currentProcess.arriveTime
//                currentProcess.lifecycle.add(TaskCard.Finished)
//                currentProcess.state = TaskCard.Finished
//                selected = null // Reset selected process if it completes
//            }
//        }
//
//        // Increment the current time
//        currentTime++
//    }
//
//
//
//    private fun getCurrentBurst(process: TaskCard): Int {
//        return process.lifecycle.count { it == TaskCard.Running }
//    }
//
//    fun getProcessTable(): List<TaskCard> {
//        return _processTable.toList() // Returning a read-only view of the list
//    }
//
//}

//ROUND ROBIN
//class Scheduler(private val processQueue: ProcessQueue) {
//    private val processTable = mutableListOf<TaskCard>()
//    var currentTime: Int = 0
//    var selected: TaskCard? = null
//    private var timeSliceRemaining: Int = 0
//
//    private val _processTable: List<TaskCard>
//        @JvmName("getProcessTableProperty") get() = processTable.toList()
//
//    fun addProcess(process: TaskCard) {
//        process.lifecycle = MutableList(currentTime + 1) { -1 }
//        processTable.add(process)
//        processQueue.enqueue(process)
//        process.state = TaskCard.New
//    }
//
//    fun updateGantt(): String {
//        val ganttChart = StringBuilder()
//        processTable.forEach { process ->
//            ganttChart.append("|${process.name} ${process.lifecycle.joinToString("") { it.toString() }}|\n")
//        }
//        return ganttChart.toString()
//    }
//
//    fun runNextStep(algorithm: Int, modality: Int, quantum: Int) {
//        // Enqueue arriving processes
//        processTable.forEach { process ->
//            if (process.arriveTime == currentTime && process.state == TaskCard.Ready) {
//                process.lifecycle.add(TaskCard.Blocked)
//                processQueue.enqueue(process)
//            }
//        }
//
//        // Preemptive and Round Robin checks
//        if (modality == Modality.PREEMPTIVE || algorithm == Algorithm.ROUND_ROBIN) {
//            handlePreemptionAndRoundRobin(algorithm, modality, quantum)
//        }
//
//        // Run the selected process for one time unit
//        selected?.let { currentProcess ->
//            runCurrentProcess(currentProcess)
//        }
//
//        // Increment the current time
//        currentTime++
//    }
//
//    private fun handlePreemptionAndRoundRobin(algorithm: Int, modality: Int, quantum: Int) {
//        // Round Robin process selection and time slice management
//        if (algorithm == Algorithm.ROUND_ROBIN) {
//            if (selected == null || timeSliceRemaining == 0) {
//                selected?.let {
//                    if (!it.completed) {
//                        it.lifecycle.add(TaskCard.Blocked)
//                        processQueue.enqueue(it)
//                    }
//                }
//                selected = processQueue.dequeue()
//                timeSliceRemaining = quantum
//            }
//        }
//
//        // Handle preemption in a preemptive policy
//        if (modality == Modality.PREEMPTIVE && selected != null && !selected!!.completed) {
//            preemptiveCheck(algorithm)
//        }
//    }
//
//    private fun preemptiveCheck(algorithm: Int) {
//        val moreUrgentProcess = processTable.filter {
//            it.arriveTime <= currentTime && !it.completed && it.state == TaskCard.Ready
//        }.minByOrNull { if (algorithm == Algorithm.PRIORITIES) it.priority else it.burst }
//
//        if (moreUrgentProcess != null && ((algorithm == Algorithm.PRIORITIES && moreUrgentProcess.priority < selected!!.priority) ||
//                    (algorithm == Algorithm.SJF && moreUrgentProcess.burst < selected!!.burst))
//        ) {
//            processQueue.enqueue(selected!!)
//            selected!!.lifecycle.add(TaskCard.Blocked)
//            selected = null
//        }
//    }
//
//    private fun runCurrentProcess(currentProcess: TaskCard) {
//        val currentBurst = getCurrentBurst(currentProcess)
//        if (currentBurst < currentProcess.burst) {
//            currentProcess.lifecycle.add(TaskCard.Running)
//            currentProcess.state = TaskCard.Running
//            timeSliceRemaining--
//        }
//
//        // Update lifecycle for all other processes
//        processTable.forEach { process ->
//            if (process != currentProcess && process.arriveTime <= currentTime && !process.completed) {
//                process.lifecycle.add(TaskCard.Blocked)
//                process.waitingTime++
//                process.state = TaskCard.Blocked
//            }
//        }
//
//        // Check if the current process is completed
//        if (currentBurst + 1 >= currentProcess.burst) {
//            currentProcess.completed = true
//            currentProcess.returnTime = currentTime + 1 - currentProcess.arriveTime
//            currentProcess.lifecycle.add(TaskCard.Finished)
//            currentProcess.state = TaskCard.Finished
//            selected = null // Reset selected process if it completes
//            timeSliceRemaining = 0
//        }
//    }
//
//    private fun getCurrentBurst(process: TaskCard): Int {
//        return process.lifecycle.count { it == TaskCard.Running }
//    }
//
//    fun getProcessTable(): List<TaskCard> {
//        return _processTable.toList() // Returning a read-only view of the list
//    }
//}

// HRRN
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

        if (algorithm == Algorithm.ROUND_ROBIN) {
            handleRoundRobin(quantum)
        } else if (modality == Modality.PREEMPTIVE) {
            handlePreemption(algorithm)
        } else if (algorithm == Algorithm.HRRN) {
            handleHRRN()
        }

        selected?.let { runCurrentProcess(it) }

        currentTime++
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
        }.minByOrNull { if (algorithm == Algorithm.PRIORITIES) it.priority else it.burst }

        if (moreUrgentProcess != null && ((algorithm == Algorithm.PRIORITIES && moreUrgentProcess.priority < selected!!.priority) ||
                    (algorithm == Algorithm.SJF && moreUrgentProcess.burst < selected!!.burst))
        ) {
            processQueue.enqueue(selected!!)
            selected!!.lifecycle.add(TaskCard.Blocked)
            selected = null
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
        return (waitingTime + process.burst).toDouble() / process.burst
    }

    private fun runCurrentProcess(currentProcess: TaskCard) {
        if (currentProcess.responseTime == null) {
            currentProcess.responseTime = currentTime - currentProcess.arriveTime
        }

        val currentBurst = getCurrentBurst(currentProcess)
        if (currentBurst < currentProcess.burst) {
            currentProcess.lifecycle.add(TaskCard.Running)
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
        if (currentBurst + 1 >= currentProcess.burst) {
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
        return process.lifecycle.count { it == TaskCard.Running }
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





