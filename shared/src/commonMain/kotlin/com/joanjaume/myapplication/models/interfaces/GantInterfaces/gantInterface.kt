package com.joanjaume.myapplication.models.interfaces.gantInterface


class GanttTask(val name: String, val cpuCycles: Long) {
    var id: Int = 0
    var startTime: Long = 0
    var endTime: Long = 0
}