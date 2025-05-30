package com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import com.joanjaume.myapplication.android.components.CardCreator.AlgorithmCardCreator
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard

@Composable
fun StepTwo(viewModel: SimulationViewModel) {
    LaunchedEffect(key1 = true) {
        viewModel.initializeStepTwo()
    }

    val algorithmCards = viewModel.algorithmCards.observeAsState(emptyList())
    val selectedAlgorithmCard = viewModel.selectedAlgorithmCard.observeAsState(initial = null as AlgorithmCard?)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color(0xFF006400), RoundedCornerShape(8.dp))
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
        if (algorithmCards.value?.isNotEmpty() == true) {
            LazyRow {
                items(algorithmCards.value!!) { card ->
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
        if (selectedAlgorithmCard.value != null) {
            CardComposable(
                selectedAlgorithmCard.value!!,
                handleClickCard = { Unit }
            )
        } else {
            Text(
                text = "NO SELECTED CARD YET",
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
        //CREATE
        AlgorithmCardCreator(viewModel = viewModel)
    }
}
