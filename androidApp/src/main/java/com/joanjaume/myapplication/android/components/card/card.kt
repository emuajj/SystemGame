package com.joanjaume.myapplication.android.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.joanjaume.myapplication.models.interfaces.cardInterface.*


@Composable
fun CardComposable(card: ICardGeneric, handleClickCard: (ICardGeneric) -> Unit) {
    val colorTitle = if (card.type == CardType.CPU) {
        Color.Red
    } else if (card.type == CardType.ALGORITHM) {
        Color(0xFF006400)
    } else {
        MaterialTheme.colors.primary
    }



    Card(
        modifier = Modifier
            .padding(1.dp)
            .clickable { handleClickCard(card) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.h6,
                color = colorTitle
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${card.type}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.secondary
            )
            Surface(
                modifier = Modifier.padding(1.dp),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colors.background // Set the background color you want
            ) {
                Column(
                    modifier = Modifier
                        .padding(1.dp)
                        .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    if (card is TaskCard) {
                        Text(
                            text = "BURST :" + card.burst + "  In/Out :" + card.ioDuration,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = "PRIORITY :" + card.priority,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                    } else if (card is AlgorithmCard) {
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
                        Text(
                            text = "Algorithm : " + Algorithm[card.algorithm],
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                    } else if (card is CpuCard) {
                        Text(
                            text = "Clock Speed : " + card.clockSpeed,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            text = "Description : " + card.description,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface
                        )
                    } else {

                    }
                }
            }
        }
    }
}