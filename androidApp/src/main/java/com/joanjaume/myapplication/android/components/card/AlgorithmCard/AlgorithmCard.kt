package com.joanjaume.myapplication.android.components.card.AlgorithmCard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.models.interfaces.cardInterface.*

@Composable
fun AlgorithmCardComposable(card: AlgorithmCard, handleClickCard: (ICardGeneric) -> Unit) {
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
                text = "ALGORITHM",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Name: ${card.name}",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Algorithm : ${Algorithm[card.algorithm]}",
                style = MaterialTheme.typography.body2,
            )
            if (card.algorithm == 3) {
                Text(
                    text = "Quantum : ${card.quantum}",
                    style = MaterialTheme.typography.body2,
                )
            } else {
                Text(
                    text = "Modality : ${Modality[card.modality]}",
                    style = MaterialTheme.typography.body2,
                )
            }

        }
    }
}