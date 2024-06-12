package com.joanjaume.myapplication.models.interfaces.gantInterface

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

enum class ITaskStates { New, Ready, Running, Waiting, Terminated }


interface IResults {
    var completedProcesses: List<TaskCard>
    var averageWaitingTime: Double
    var averageResponseTime: Double
    var averageReturnTime: Double
}

data class Results(
    override var completedProcesses: List<TaskCard>,
    override var averageWaitingTime: Double,
    override var averageResponseTime: Double,
    override var averageReturnTime: Double
) : IResults