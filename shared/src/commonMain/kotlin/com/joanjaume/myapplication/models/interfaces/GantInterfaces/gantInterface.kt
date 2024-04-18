package com.joanjaume.myapplication.models.interfaces.gantInterface

import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard


class GanttTask(card :TaskCard,time :Long) {
    var id: Int = card.cardId!!
    var startTime: Long = time
    var endTime: Long = time + card.cpuCycles
    var card: TaskCard = card
}