import kotlin.jvm.Synchronized

class Task(val name: String, val cpuCycles: Long) {
    var id: Int = 0
    var startTime: Long = 0
    var endTime: Long = 0
}

class GanttChart {
    private val tasks: MutableMap<Int, Task> = mutableMapOf()
    private var nextTaskId = 1
    private var currentTime: Long = 0

    @Synchronized
    private fun generateTaskId(): Int {
        return nextTaskId++
    }

    fun addTask(task: Task): Task {
        val taskId = generateTaskId()
        task.id = taskId
        tasks[taskId] = task
        return task
    }

    fun removeTask(taskId: Int) {
        tasks.remove(taskId)
    }

    fun iterateTime() {
        currentTime++
        updateTaskTimes()
    }

    private fun updateTaskTimes() {
        for ((taskId, task) in tasks) {
            if (task.startTime == 0L) {
                task.startTime = currentTime
            }
            task.endTime = task.startTime + task.cpuCycles
        }
    }

    fun displayChart() {
        // Implement Gantt Chart rendering logic here
        for ((taskId, task) in tasks) {
            println("Task ID: $taskId, Task: ${task.name}, Start Time: ${task.startTime} seconds, End Time: ${task.endTime} seconds")
        }
    }
}