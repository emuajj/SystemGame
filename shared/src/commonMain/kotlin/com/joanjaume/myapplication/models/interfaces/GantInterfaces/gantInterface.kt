package com.joanjaume.myapplication.models.interfaces.gantInterface

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard

enum class ITaskStates { New, Ready, Running, Waiting, Terminated }

//class GanttTask(card: TaskCard, startTime: Long, endTime: Long, state: ITaskStates) {
//    var id: Int = card.cardId!!
//    var startTime: Long? = null
//    var endTime: Long? = null
//    var state: ITaskStates = ITaskStates.New
//    var card: TaskCard = card
//}
//
//
//data class Process(
//    val id: Int,
//    var name: String,
//    var arriveTime: Int,
//    var burst: Int,
//    var priority: Int,
//    var completed: Boolean = false,
//    var lifecycle: MutableList<Int> = mutableListOf(),
//    var waitingTime: Int = 0,
//    var responseTime: Int = 0,
//    var returnTime: Int = 0
//)

// New, Ready, Running, Waiting, Terminated