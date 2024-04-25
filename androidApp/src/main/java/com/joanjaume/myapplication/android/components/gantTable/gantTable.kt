package com.joanjaume.myapplication.android.components.ganttTable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
    tasks: Map<Int, GanttTask>,
    iteration : Long,
    modifier: Modifier = Modifier,
    maxTime: Int
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
                Text("name", modifier = Modifier.width(60.dp).padding(4.dp), style = MaterialTheme.typography.subtitle2)
                for (time in 1..maxTime) {
                    Spacer(modifier = Modifier.width(4.dp)) // Adjust spacing as necessary
                    // Determine the background color based on whether iteration equals time
                    val backgroundColor = if (iteration >= time.toLong()) Color.Green else Color.Transparent

                    // Wrap the Text in a Box to set the background
                    Box(
                        modifier = Modifier
                            .width(60.dp) // Match width with the header
                            .padding(4.dp)
                            .background(backgroundColor) // Set the background color
                            .padding(4.dp) // Padding inside the Box, around the Text
                    ) {
                        Text(
                            text = time.toString(),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                }
            }

            // Horizontal divider after the header
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Rows for each task
            tasks.values.forEach { task ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = task.card.name + task.card.cardId,
                        modifier = Modifier.width(60.dp).padding(4.dp), // Match width with the header
                        style = MaterialTheme.typography.body2
                    )
                    // Time units for the task
                    for (time in 1..maxTime) {
                        Spacer(modifier = Modifier.width(4.dp)) // Adjust spacing as necessary
                        Box(modifier = Modifier
                            .width(60.dp) // Match width with the header
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp)) // Optional: clip to round corners
                            .background(
                                if (time >= task.startTime && time <= task.endTime) Color.Red else Color.Transparent
                            )
                        )
                    }
                }

                // Horizontal divider after each task
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}
