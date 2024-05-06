package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    TASK,
    CPU,
    GENERIC
}

interface ICardGeneric {
    var cardId: Int?
    val title: String
    val type: CardType
    val description: String
    val name: String
}

interface ITaskCard : ICardGeneric {
    val cueTime: Int
    val cpuCycles: Int
}

interface ICpuCard : ICardGeneric {
    val clockSpeed: Int
}

data class TaskCard(
    override var cardId : Int?,
    override val title: String,
    override val type: CardType = CardType.TASK,
    override val description: String,
    override val name: String,
    override val cueTime: Int,
    override var cpuCycles: Int,
) : ITaskCard

data class CpuCard(
    override var cardId : Int?,
    override val title: String,
    override val type: CardType = CardType.CPU,
    override val description: String,
    override val name: String,
    override val clockSpeed: Int,
) : ICpuCard {
    private var currentTask: TaskCard? = null  // Track the current task

    fun isAvailable(): Boolean {
        return currentTask == null  // CPU is available if there's no current task
    }

    fun assignTask(task: TaskCard) {
        currentTask = task  // Assign a new task to this CPU
    }

    fun releaseTask() {
        currentTask = null  // Release the current task
    }
}