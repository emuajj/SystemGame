package com.joanjaume.myapplication.models.gameBoard

import GanttChart
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import com.joanjaume.myapplication.models.interfaces.gantInterface.ITaskStates

class GameBoard(deck: Deck) {
    var actualTasks: MutableList<TaskCard> = mutableListOf()
    var actualCpus: MutableList<CpuCard> = mutableListOf()
    var ganttChart: GanttChart = GanttChart()

    init {
        for (card in deck.getDeck()) {
            when (card) {
                is TaskCard -> actualTasks.add(card)
                is CpuCard -> actualCpus.add(card)
            }
        }
    }

    fun setGantt(ganttChart: GanttChart) {
        this.ganttChart = ganttChart
    }

    fun getGanttChart(): Map<Int, GanttTask> {
        return ganttChart.getGanttChart()
    }

    fun iterateTime(): Long {
        return ganttChart.iterateTime()
    }

    fun addTaskToGantt(taskCard: TaskCard) {
        if (taskCard.cardId != null) {
            var time = ganttChart.getTime()
            ganttChart.addTask(GanttTask(taskCard, time))
        }
    }
}