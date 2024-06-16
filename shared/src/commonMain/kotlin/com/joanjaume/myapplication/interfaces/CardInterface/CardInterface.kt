package com.joanjaume.myapplication.models.interfaces.cardInterface

enum class CardType {
    TASK,
    CPU,
    ALGORITHM,
    GENERIC
}


interface ICardGeneric {
    val id: Int?
    val name: String
    val type: CardType
    val description: String?
}

interface ITaskCard : ICardGeneric {
    val burst: MutableList<String>
    var priority: Int
    var lifecycle: MutableList<Int>
    var waitingTime: Int
    var returnTime: Int
    var responseTime: Int?
    var completed: Boolean
    var startTime: Int?
    var endTime: Int?
    var state: Int
}

interface ICpuCard : ICardGeneric {
    val clockSpeed: Int
}

interface IAlgorithmCard : ICardGeneric {
    val modality: Int
    val algorithm: Int
    val quantum: Int?

}

data class TaskCard(
    override val id: Int?,
    override val name: String,
    override val type: CardType = CardType.TASK,
    override val description: String?,
    var arriveTime: Int,
    override var burst: MutableList<String> = mutableListOf(),
    override var priority: Int,
    override var lifecycle: MutableList<Int> = mutableListOf(), // Changed to store state changes as strings
    var currentBurst: Int = 0,
    override var waitingTime: Int = 0,
    override var returnTime: Int = 0,
    override var responseTime: Int? = null,
    override var completed: Boolean = false,
    override var startTime: Int? = null,
    override var endTime: Int? = null,
    override var state: Int = New,
) : ITaskCard {
    companion object LifecycleState {
        const val New = 0
        const val Blocked = 1
        const val Ready = 2
        const val Running = 3
        const val Finished = 4
        const val PerformingIO = 5  // State for performing I/O
    }
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

data class AlgorithmCard(
    override val id: Int?,
    override val name: String,
    override val description: String?,
    override val type: CardType = CardType.ALGORITHM,
    override val modality: Int,
    override val algorithm: Int,
    override val quantum: Int? = null
) : IAlgorithmCard {

}


object Modality {
    operator fun get(modality: Int): String {
        return when (modality) {
            PREEMPTIVE -> "Preemptive"
            NON_PREEMPTIVE -> "Non-preemptive"
            else -> "Unknown modality"
        }
    }

    const val PREEMPTIVE = 1
    const val NON_PREEMPTIVE = 2
}

object Algorithm {
    const val SJF = 1
    const val PRIORITIES = 2
    const val ROUND_ROBIN = 3
    const val HRRN = 4

    operator fun get(algorithm: Int): String {
        return when (algorithm) {
            SJF -> "Shortest Job First"
            PRIORITIES -> "Priority Scheduling"
            ROUND_ROBIN -> "Round Robin"
            HRRN -> "Highest Response Ratio Next"
            else -> "Unknown Algorithm"
        }
    }
}