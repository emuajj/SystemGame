package com.joanjaume.myapplication.models.scheduler

import com.joanjaume.myapplication.models.interfaces.cardInterface.Algorithm
import com.joanjaume.myapplication.models.interfaces.cardInterface.Modality
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue
import kotlin.jvm.JvmName


class Scheduler(private val processQueue: ProcessQueue) {
    private val processTable = mutableListOf<TaskCard>()
    var currentTime: Int = 0
    var selected: TaskCard? = null

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

    fun runNextStep(algorithm: Int, modality: Int) {

        // Enqueue arriving processes
        processTable.forEach { process ->
            if (process.arriveTime == currentTime && process.state == TaskCard.Ready) {
                process.lifecycle.add(TaskCard.Blocked)
                processQueue.enqueue(process)
            }

            if (selected != null && modality == Modality.PREEMPTIVE && !process.completed && process.state == TaskCard.Ready) {
                if (selected!!.burst > process.burst || selected!!.priority > process.priority) {
                    processQueue.enqueue(selected!!)
                    process.lifecycle.add(TaskCard.Blocked)
                    selected = null
                }
            }
        }

        // Select the next process to run
        if (selected == null && processQueue.size() > 0) {
            if (algorithm == Algorithm.SJF && processQueue.size() > 1) {
                val list = processQueue.getList().sortedBy { it.burst }
                processQueue.setList(list)
            } else if (algorithm == Modality.PREEMPTIVE && processQueue.size() > 1) {
                val list = processQueue.getList().sortedBy { it.priority }
                processQueue.setList(list)
            }
            selected = processQueue.dequeue()
        }

        // Run the selected process for one time unit
        selected?.let { currentProcess ->
            val currentBurst = getCurrentBurst(currentProcess)
            if (currentBurst < currentProcess.burst) {
                currentProcess.lifecycle.add(TaskCard.Running)
                currentProcess.state = TaskCard.Running
            }

            // Update lifecycle for all processes
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
            }
        }

        // Increment the current time
        currentTime++

    }


    private fun getCurrentBurst(process: TaskCard): Int {
        return process.lifecycle.count { it == TaskCard.Running }
    }

    fun getProcessTable(): List<TaskCard> {
        return _processTable.toList() // Returning a read-only view of the list
    }
    
}