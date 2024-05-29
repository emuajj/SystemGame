package com.joanjaume.myapplication.android.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.MyApplicationTheme
import com.joanjaume.myapplication.android.components.card.AlgorithmCard.AlgorithmCardComposable
import com.joanjaume.myapplication.android.components.card.CardComposable
import com.joanjaume.myapplication.android.components.card.CpuCard.CpuCardComposable
import com.joanjaume.myapplication.android.components.ganttTable.GanttChartComponent
import com.joanjaume.myapplication.android.`view-models`.ViewModel
import com.joanjaume.myapplication.models.gameModels.Model
import com.joanjaume.myapplication.models.interfaces.cardInterface.AlgorithmCard
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.CpuCard

@Composable
fun SimulationScreen() {

}