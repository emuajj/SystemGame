package com.joanjaume.myapplication.android.components.gantTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask

@Composable
fun GanttChartComponent(
    tasks: Map<Int, GanttTask>,
    modifier: Modifier, // Combined width and height modifiers
    maxTime: Long
) {
    Surface(
        modifier = modifier
            .background(color = Color.White)
            .padding(8.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Render Time Iterations
            Row(modifier = Modifier.padding(end = 4.dp)) {
                repeat(maxTime.toInt()) { iteration ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Show iteration number
                    }
                }
            }

            // Render Tasks and Progress
            tasks.forEach { (id, task) ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Task Name
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp)
                            .aspectRatio(1f)
                            .background(color = Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        // Show task name
                    }

                    // Task Progress
                    task.cpuCycles.let { cycles ->
                        val progress = (task.endTime - task.startTime) / cycles.toFloat()
                        val progressWidth = (progress * maxTime).toInt()

                        Box(
                            modifier = Modifier
                                .weight(5f)
                                .padding(vertical = 4.dp)
                                .fillMaxWidth(progressWidth.toFloat() / maxTime.toFloat())
                                .background(color = Color.Blue)
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            // Show progress
                        }
                    }
                }
            }
        }
    }
}
