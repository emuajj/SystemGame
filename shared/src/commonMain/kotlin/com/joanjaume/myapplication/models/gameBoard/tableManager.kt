import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask
import kotlin.jvm.Synchronized

class GanttChart() {
    private val tasks: MutableMap<Int, GanttTask> = mutableMapOf()
    private var nextTaskId = 1
    private var currentTime: Long = 0

    @Synchronized
    private fun generateTaskId(): Int {
        return nextTaskId++
    }

    fun addTask(task: GanttTask): GanttTask {
        val taskId = generateTaskId()
        task.id = taskId
        tasks[taskId] = task
        return task
    }

    fun removeTask(taskId: Int) {
        tasks.remove(taskId)
    }

    fun iterateTime(): Long {
        currentTime++
        return currentTime;
//        updateTaskTimes()
    }

    fun getTime() : Long{
        return currentTime
    }

//    private fun updateTaskTimes() {
//        for ((taskId, task) in tasks) {
//            if (task.startTime == 0L) {
//                task.startTime = currentTime
//            }
//            task.endTime = task.startTime + task.cpuCycles
//        }
//    }

    fun getGanttChartt(): Map<Int, GanttTask> {
        return tasks.toMap()
    }
}