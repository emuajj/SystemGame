package com.joanjaume.myapplication.android.components.ui.Stepper

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Stepper(
    value: Int,
    size: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 1,
    maxValue: Int = size
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { if (value > minValue) onValueChange(value - 1) },
            enabled = value > minValue
        ) {
            Text("<")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = value.toString())

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { if (value < maxValue) onValueChange(value + 1) },
            enabled = value < maxValue
        ) {
            Text(">")
        }
    }
}