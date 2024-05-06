package com.joanjaume.myapplication.models.interfaces.gantInterface

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

enum class ITaskStates { New , Ready , Running , Waiting , Terminated}

class GanttTask(card :TaskCard,time :Long) {
    var id: Int = card.cardId!!
    var startTime: Long = time
    var endTime: Long = time + card.cpuCycles
    var state: ITaskStates = ITaskStates.New
    var card: TaskCard = card
}

// New, Ready, Running, Waiting, Terminated