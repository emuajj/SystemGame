package com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.android.components.CardCreator.TaskCardCreator

@Composable
fun StepOne(viewModel: SimulationViewModel) {
    LaunchedEffect(key1 = true) {
        viewModel.initializeStepOne()
    }

    val taskCards = viewModel.taskCards.observeAsState(emptyList())
    val tasksSelected = viewModel.selectedTaskCards.observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Available Cards:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bowld
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        if (taskCards.value?.isNotEmpty() == true) {
            LazyRow {
                items(taskCards.value!!) { card ->
                    CardComposable(
                        card,
                        handleClickCard = { viewModel.handleClickCardStepper(card) }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        } else {
            Text(
                text = "THERE ARE NO CARDS TO SHOW",
                style = TextStyle(
                    fontSize = 20.sp, // Change the font size to 20 sp
                    fontWeight = FontWeight.Light // Optional: make the text bold
                ),
                modifier = Modifier.padding(8.dp) // Adds padding around the text
            )
        }
        Text(
            text = "Selected Cards for the simulation:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bold
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        if (tasksSelected.value?.isNotEmpty() == true) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()  // Ensures the LazyColumn fills the horizontal space
                    .heightIn(max = 210.dp)
            ) {
                items(tasksSelected.value!!) { card ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()  // Ensures the Row fills the horizontal space
                            .padding(2.dp)  // Adds padding around each Row for better spacing
                            .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = card.name,
                            modifier = Modifier
                                .weight(1f)  // Allocates a fraction of the Row's space to this Text
                                .padding(end = 8.dp)  // Adds padding to the end of the Text
                        )
                        Text(
                            text = "Burst: ${card.burst}",
                            modifier = Modifier
                                .weight(1f)  // Ensures this Text also takes a significant portion of the space
                        )
                        Text(
                            text = "Priority: ${card.priority}",
                            modifier = Modifier
                                .weight(1f)  // Ensures this Text also takes a significant portion of the space
                        )
                    }
                }
            }
        } else {
            Text(
                text = "NO SELECTED CARDS YET",
                style = TextStyle(
                    fontSize = 20.sp, // Change the font size to 20 sp
                    fontWeight = FontWeight.Light // Optional: make the text bold
                ),
                modifier = Modifier.padding(8.dp) // Adds padding around the text
            )
        }
        Text(
            text = "Create your own cards for the simulation:",
            style = TextStyle(
                fontSize = 20.sp, // Change the font size to 20 sp
                fontWeight = FontWeight.Bold // Optional: make the text bold
            ),
            modifier = Modifier.padding(8.dp) // Adds padding around the text
        )
        TaskCardCreator(viewModel)
    }
}
