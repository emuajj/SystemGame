package com.joanjaume.myapplication.android.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.components.Stepper.Stepper
import com.joanjaume.myapplication.android.components.Stepper.Steps.SimulationSteps.StepOne
import com.joanjaume.myapplication.android.`view-models`.SimulationViewModel

class SimulationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimulationComposable()
        }
    }
}

@Composable
fun SimulationComposable() {
    val viewModel: SimulationViewModel = SimulationViewModel()
    val stepperValue = viewModel.stepperStep.observeAsState(1)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Stepper(
                    value = stepperValue.value,
                    size = 3,
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
                }
            }
        }
    }

}