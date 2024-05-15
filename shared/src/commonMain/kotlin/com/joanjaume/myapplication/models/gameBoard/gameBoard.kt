package com.joanjaume.myapplication.models.gameBoard

import GanttChart
import com.joanjaume.myapplication.models.deck.Deck
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.ICardGeneric
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import com.joanjaume.myapplication.models.scheduler.Scheduler

class GameBoard() {
    private var deck = Deck()
    var actualTasks: MutableList<TaskCard> = mutableListOf()
    var actualCpus: MutableList<CpuCard> = mutableListOf()


    // unitats de CPU disponibles
    var numberOfCpus: Int = 0;

    //SCHELUDE MANAGING
    var ganttChart: GanttChart = GanttChart()
    private var scheduler: Scheduler = Scheduler(ganttChart)

    init {
        for (card in deck.getDeck()) {
            when (card) {
                is TaskCard -> actualTasks.add(card)
            }
        }
    }

    fun setGantt(ganttChart: GanttChart) {
        this.ganttChart = ganttChart
    }

    fun getGanttChart(): Map<Int, GanttTask> {
        return ganttChart.getGanttChart()
    }

    fun onTaskCardClicked(taskCard: TaskCard) {
//        actualTasks.add(taskCard)
        addTaskToScheduler(taskCard)
    }

    fun addTaskToScheduler(taskCard: TaskCard) {
        if (taskCard.cardId != null) {
            scheduler.addTask(taskCard)
            // Optionally run the scheduler immediately
            scheduler.runScheduler()
        }
    }

    fun updateGanttChart() {
        scheduler.runScheduler()
        ganttChart.iterateTime()
    }

    fun iterateTime(): Long {
        updateGanttChart()
        return ganttChart.getTime()
    }

    fun getCpus(): List<CpuCard> {
        return actualCpus
    }

    //MANAGING CPU

    fun handleChangeCpus(cpus: List<CpuCard>) {
        actualCpus = cpus.toMutableList()
    }


    //DECK
    fun getDeck(): List<ICardGeneric> {
        return deck.getDeck()
    }

    fun addCardToDeck() {
        deck.addCard(
            TaskCard(
                null,
                "Card 1",
                CardType.TASK,
                "Description for Card 1",
                "John Doe",
                5,
                3
            )
        )
    }

    fun removeOneCardFromDeck(CardId: Int) {
        deck.removeCard(CardId)
    }

    fun setDeck(sentDeck: List<ICardGeneric>) {
        deck.setDeck(sentDeck)
    }

    fun removeOneCardFromDeck() {

    }
}