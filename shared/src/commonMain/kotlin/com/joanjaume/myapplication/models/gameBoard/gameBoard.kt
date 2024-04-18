package com.joanjaume.myapplication.models.gameBoard

import GanttChart
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ITaskCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask

class GameBoard(deck: Deck) {
    var actualTasks: MutableList<TaskCard> = mutableListOf()
    var actualCpus: MutableList<CpuCard> = mutableListOf()
    var ganttChart: GanttChart = GanttChart()

    init {
        for (card in deck.getDeck()) {
            if (card.type == CardType.TASK && card is TaskCard) {
                actualTasks.add(card)
            } else if (card.type == CardType.CPU && card is CpuCard) {
                actualCpus.add(card)
            }
        }
    }

    fun setGantt(ganttChart: GanttChart) {
        this.ganttChart = ganttChart
    }

    fun getGanttChartt(): Map<Int, GanttTask> {
        return ganttChart.getGanttChartt()
    }

    fun addTaskToGantt(taskCard: TaskCard) {
        if (taskCard.cardId != null) {
            var time = ganttChart.getTime()
            ganttChart.addTask(
                GanttTask(
                    taskCard,
                    time
                )
            )  // Add task to the GanttChart managed by GameBoard
        }
    }
}