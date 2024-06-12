package com.joanjaume.myapplication.android.components.ui.Results

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joanjaume.myapplication.models.interfaces.gantInterface.Results

@Composable
fun ShowResultsModal(toggleResultsModal: (Boolean) -> Unit, results: Results) {

    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Simulation Results", style = MaterialTheme.typography.h6)

            Text(
                "Average Waiting Time: ${results.averageWaitingTime}",
                style = MaterialTheme.typography.body1
            )
            Text(
                "Average Response Time: ${results.averageResponseTime}",
                style = MaterialTheme.typography.body1
            )
            Text(
                "Average Return Time: ${results.averageReturnTime}",
                style = MaterialTheme.typography.body1
            )

            Spacer(Modifier.height(10.dp))

//                    results.completedProcesses.forEach { task ->
//                        TaskCardView(task)
//                    }

            Button(
                onClick = { toggleResultsModal(false) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Close")
            }
        }

    }
}