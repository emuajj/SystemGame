package com.joanjaume.myapplication.models.scheduler

import GanttChart
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import com.joanjaume.myapplication.models.interfaces.gantInterface.ITaskStates

class Scheduler(private val ganttChart: GanttChart) {
    private val taskQueue = mutableListOf<GanttTask>()

    fun addTask(taskCard: TaskCard) {
        val startTime = calculateStartTime(taskCard)
        val endTime = startTime + taskCard.cpuCycles

        val ganttTask = GanttTask(
            card = taskCard,
            startTime = startTime,
            endTime = endTime,
            state = ITaskStates.New
        )

        ganttChart.addTask(ganttTask)
        taskQueue.add(ganttTask)
        ganttTask.state = ITaskStates.Ready
    }

    private fun calculateStartTime(taskCard: TaskCard): Long {
        // Placeholder for actual scheduling logic
        return ganttChart.getTime()
    }

    fun runScheduler() {
        taskQueue.forEach { task ->
            if (task.state == ITaskStates.Ready && ganttChart.getTime() >= task.startTime!!) {
                task.state = ITaskStates.Running
                ganttChart.updateTaskState(task.id, ITaskStates.Running)
            }
            if (ganttChart.getTime() >= task.endTime!! && task.state != ITaskStates.Terminated) {
                task.state = ITaskStates.Terminated
                ganttChart.updateTaskState(task.id, ITaskStates.Terminated)
            }
        }
    }
}
