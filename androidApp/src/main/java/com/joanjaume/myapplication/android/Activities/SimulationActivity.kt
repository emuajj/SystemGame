package com.joanjaume.myapplication.android.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.joanjaume.myapplication.android.components.ui.Stepper.Stepper
import com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps.StepOne
import com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps.StepThree
import com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps.StepTwo
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.android.components.ui.Stepper.Steps.SimulationSteps.StepFour
import com.joanjaume.myapplication.android.screens.Simulation.SimulationGame

class SimulationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the userId passed from MyApp
        val userId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")

        // Use userId and userName as needed
        println("User ID: $userId")
        println("User Name: $userName")
        setContent {
            if (userId != null) {
                SimulationComposable(userId = userId)
            }
        }
    }
}

@Composable
fun SimulationComposable(userId: String) {
    val viewModel = SimulationViewModel(userId)
    val stepperValue = viewModel.stepperStep.observeAsState(1)
    var showStepper by remember { mutableStateOf(true) }

    fun handleStartSimulation() {
        showStepper = false
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showStepper) {
                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    Stepper(
                        value = stepperValue.value,
                        size = 4,
                        onValueChange = { newValue -> viewModel.onStepperValueChange(newValue) }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp) // Set the height of the line
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    when (stepperValue.value) {
                        1 -> StepOne(viewModel)
                        2 -> StepTwo(viewModel)
                        3 -> StepThree(viewModel)
                        4 -> StepFour(viewModel) { handleStartSimulation() }
                    }
                }
            } else {
                if (viewModel.selectedAlgorithmCard.value != null && viewModel.selectedCpuCard.value != null && viewModel.selectedTaskCards.value?.isNotEmpty() == true)
                    SimulationGame(
                        tasksList = (viewModel.selectedTaskCards.value!!),
                        cpuCard = (viewModel.selectedCpuCard.value!!),
                        algorithmCard = (viewModel.selectedAlgorithmCard.value!!)
                    )
            }
        }
    }
}