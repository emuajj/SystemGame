package com.joanjaume.myapplication.android.components.ganttTable

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard


@Composable
fun GanttChartComponent(
    tasks: List<TaskCard>,
    iteration: Int,
    modifier: Modifier = Modifier,
    shape: Shape,
    maxTime: Int
) {
    val unitWidth = 64.dp
    val scrollStateHorizontal = rememberScrollState()
    val scrollStateVertical = rememberScrollState()

    val density = LocalDensity.current
    val centeredIteration = maxOf(1, iteration - 2)
    val scrollPositionPx = with(density) { (centeredIteration * unitWidth.toPx()).toInt() }

    LaunchedEffect(iteration) {
        scrollStateHorizontal.animateScrollTo(scrollPositionPx)
    }

    Surface(
        modifier = modifier
            .background(color = Color.White)
            .padding(8.dp),
        color = MaterialTheme.colors.surface,
        elevation = 4.dp,
        shape = shape
    ) {
        Column {
            // Header row outside the vertically scrollable column
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.width(60.dp)) {
                    Text(
                        "name",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                // Time headers scrollable horizontally but fixed vertically
                Row(modifier = Modifier.horizontalScroll(scrollStateHorizontal)) {
                    for (time in 1..maxTime) {
                        Spacer(modifier = Modifier.width(4.dp)) // Adjust spacing as necessary
                        val backgroundColor =
                            if (iteration >= time) Color(0xFFADD8E6) else Color.Transparent

                        Box(
                            modifier = Modifier
                                .width(60.dp) // Match width with the header
                                .height(26.dp)
                                .background(backgroundColor), // Set the background color
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time.toString(),
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }
                }
            }

            // Horizontal divider after the header
            Divider(color = Color.LightGray, thickness = 1.dp)

            // Vertically scrollable column for task rows
            Column(modifier = Modifier.verticalScroll(scrollStateVertical)) {
                tasks.forEach { task ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.width(60.dp)) {
                            Text(
                                text = task.name + "( p : ${task.priority})",
                                modifier = Modifier.padding(4.dp), // Match width with the header
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row(modifier = Modifier.horizontalScroll(scrollStateHorizontal)) {
                            for (time in 1..maxTime) {
                                val textToShow =
                                    if (task.lifecycle.size > time) {
                                        when (task.lifecycle[time]) {
                                            0 -> "NEW"
                                            1 -> "BLK"
                                            2 -> "RED"
                                            3 -> "CPU"
                                            4 -> "FIN"
                                            5 -> "I/O"
                                            else -> ""
                                        }
                                    } else {
                                        ""
                                    }
                                Spacer(modifier = Modifier.width(4.dp)) // Adjust spacing as necessary
                                Box(
                                    modifier = Modifier
                                        .background(
                                            if (task.lifecycle.size > time) {
                                                when (task.lifecycle[time]) {
                                                    0 -> Color.Yellow
                                                    1 -> Color.Red
                                                    2 -> Color.Blue
                                                    3 -> Color(0xFF00A000) // Dark Green
                                                    4 -> Color(0xFF505050)
                                                    5 -> Color(0xFFFFB000)
                                                    else -> Color.Transparent
                                                }
                                            } else {
                                                Color.Transparent // Provide a default color if condition is not met
                                            }
                                        )
                                        .width(60.dp)
                                        .padding(16.dp)  // Ensure internal padding to prevent overflow
                                        .clip(RoundedCornerShape(10.dp)),  // Optional: clip to round corners
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = textToShow,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // Horizontal divider after each task
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}
