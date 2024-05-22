package com.joanjaume.myapplication.android.components.card.CpuCard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.joanjaume.myapplication.models.interfaces.cardInterface.*


@Composable
fun CpuCardComposable(card: CpuCard, handleClickCard: (ICardGeneric) -> Unit) {

    Card(
        modifier = Modifier
            .padding(6.dp)
            .clickable { handleClickCard(card) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "CPU CARD",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Name: ${card.name}",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Speed : ${card.clockSpeed}",
                style = MaterialTheme.typography.body2,
            )

        }
    }
}