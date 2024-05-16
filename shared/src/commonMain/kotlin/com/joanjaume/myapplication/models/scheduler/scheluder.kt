package com.joanjaume.myapplication.models.scheduler

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.scheduler.process.ProcessQueue


class Scheduler(private val processQueue: ProcessQueue) {
    private val processTable = mutableListOf<TaskCard>()
    private var currentTime: Int = 0

    fun addProcess(process: TaskCard) {
        process.lifecycle = MutableList(currentTime + 1) { -1 }
        processTable.add(process)
        processQueue.enqueue(process)
        process.state = TaskCard.Ready
    }

    fun updateGantt(): String {
        val ganttChart = StringBuilder()
        processTable.forEach { process ->
            ganttChart.append("|${process.name} ${process.lifecycle.joinToString("") { it.toString() }}|\n")
        }
        return ganttChart.toString()
    }

    fun runNextStep(algorithm: Int, modality: Int) {
        processTable.sortBy { it.arriveTime }
        var selected: TaskCard? = null

        processTable.forEach { process ->
            if (process.arriveTime == currentTime) {
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

        if (selected == null && processQueue.size() > 0) {
            if (algorithm == Algorithm.SJF && processQueue.size() > 1) {
                val list = processQueue.getList().sortedBy { it.burst }
                processQueue.setList(list)
            } else if (algorithm == Algorithm.PRIORITIES && processQueue.size() > 1) {
                val list = processQueue.getList().sortedBy { it.priority }
                processQueue.setList(list)
            }
            selected = processQueue.dequeue()
        }

        selected?.let { currentProcess ->
            currentProcess.lifecycle.add(TaskCard.Running)
            processTable.forEach { process ->
                if (process.arriveTime <= currentTime && !process.completed) {
                    if (process.id == currentProcess.id && process.responseTime == 0) {
                        process.responseTime = currentTime - process.arriveTime
                    }

                    if (process.id == currentProcess.id && getCurrentBurst(process) == currentProcess.burst - 1) {
                        currentProcess.completed = true
                        process.completed = true
                        process.returnTime = currentTime + 1 - process.arriveTime
                        process.lifecycle.add(TaskCard.Finished)
                    }

                    if (process.id != currentProcess.id) {
                        process.lifecycle.add(TaskCard.Blocked)
                        process.waitingTime++
                    }
                }
            }
        }

        if (selected?.completed == true) {
            selected = null
        }

        currentTime++
    }

    private fun getCurrentBurst(process: TaskCard): Int {
        return process.lifecycle.count { it == TaskCard.Running }
    }

    object Modality {
        const val PREEMPTIVE = 1
        const val NON_PREEMPTIVE = 2
    }

    object Algorithm {
        const val SJF = 1
        const val PRIORITIES = 2
    }
}