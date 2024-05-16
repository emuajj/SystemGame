package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    TASK,
    CPU,
    GENERIC
}


interface ICardGeneric {
    val id: Int?
    val name: String
    val type: CardType
    val description: String?
}

interface ITaskCard : ICardGeneric {
    val burst: Int
    var priority: Int
    var lifecycle: MutableList<Int>
    var waitingTime: Int
    var returnTime: Int
    var responseTime: Int
    var completed: Boolean
    var startTime: Int?
    var endTime: Int?
    var state: Int
}

interface ICpuCard : ICardGeneric {
    val clockSpeed: Int
}

data class TaskCard(
    override val id: Int?,
    override val name: String,
    override val type: CardType = CardType.TASK,
    override val description: String?,
    val arriveTime: Int,
    override var burst: Int,
    override var priority: Int,
    override var lifecycle: MutableList<Int> = mutableListOf(),
    override var waitingTime: Int = 0,
    override var returnTime: Int = 0,
    override var responseTime: Int = 0,
    override var completed: Boolean = false,
    override var startTime: Int? = null,
    override var endTime: Int? = null,
    override var state: Int = LifecycleState.New
) : ITaskCard {
    companion object LifecycleState {
        const val New = 0
        const val Blocked = 1
        const val Ready = 2
        const val Running = 3
        const val Finished = 4
    }

    data class CpuCard(
        override val id: Int?,
        override val name: String,
        override val type: CardType = CardType.CPU,
        override val description: String,
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
}