package com.joanjaume.myapplication.android.components.card

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
fun CardComposable(card: ICardGeneric, handleClickCard: (ICardGeneric) -> Unit) {
    val colorTitle = if (card.type == CardType.CPU) {
        Color.Red
    } else {
        MaterialTheme.colors.primary
    }



    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { handleClickCard(card) },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = card.name + ":" + card.id,
                style = MaterialTheme.typography.h6,
                color = colorTitle
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Name: ${card.name}",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${card.type}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = card.name,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
            if (card is TaskCard) {
                Text(
                    text = "CPU CYCLES :" + card.burst,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            } else if (card is TaskCard.CpuCard) {
                Text(
                    text = "CPU CLOCKSPEED" + card.clockSpeed,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            } else {

            }
        }
    }
}