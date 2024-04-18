package com.joanjaume.myapplication.android.components.ganttTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.models.interfaces.gantInterface.GanttTask

@Composable
fun GanttChartComponent(
    tasks: Map<Int, GanttTask>, // Your task map
    modifier: Modifier = Modifier, // Default modifier
    maxTime: Int // The number of time units to display
) {
    Surface(
        modifier = modifier
            .background(color = Color.White)
            .padding(8.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Column {
            // Header row with time units
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("name", modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.subtitle2)
                for (time in 1..maxTime) {
                    Text(time.toString(), modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }

            // Rows for each task
            tasks.values.forEach { task ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Task name
                    Text(task.card.name + task.card.cardId, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.body2)
                    // Time units for the task
                    for (time in 1..maxTime) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .background(
                                if (time >= task.startTime && time <= task.endTime) Color.Red else Color.Transparent
                            )
                        )
                        // The box is colored if within the task duration
                    }
                }
            }
        }
    }
}
