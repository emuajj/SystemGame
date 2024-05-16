package com.joanjaume.myapplication.models.scheduler.process

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

// ProcessQueue.ktƒ

class ProcessQueue {
    private val queue: MutableList<TaskCard> = mutableListOf()

    fun enqueue(process: TaskCard) {
        queue.add(process)
    }

    fun dequeue(): TaskCard? {
        return if (queue.isNotEmpty()) queue.removeAt(0) else null
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    fun size(): Int {
        return queue.size
    }

    fun getList(): List<TaskCard> {
        return queue.toList()
    }

    fun setList(processes: List<TaskCard>) {
        queue.clear()
        queue.addAll(processes)
    }
}
