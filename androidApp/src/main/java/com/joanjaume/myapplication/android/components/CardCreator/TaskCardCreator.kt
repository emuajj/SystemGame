package com.joanjaume.myapplication.android.components.CardCreator

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.joanjaume.myapplication.android.ViewModels.SimulationViewModel
import com.joanjaume.myapplication.models.interfaces.cardInterface.CardType
import com.joanjaume.myapplication.models.interfaces.cardInterface.TaskCard
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp


@Composable
fun TaskCardCreator(viewModel: SimulationViewModel) {
    var burstList by remember { mutableStateOf(mutableListOf<String>()) }
    var priority by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        BurstDialog(
            onDismiss = { showDialog = false },
            burstList = burstList,
            updateBurstList = { newBurstList ->
                burstList = newBurstList.toMutableList()
            })
    }

    Column {
        Row {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Enter a name for the card") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(0.9f)
            )
            Spacer(Modifier.width(16.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            TextField(
                value = priority,
                onValueChange = { priority = it },
                label = { Text("Enter Priority Value") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(0.9f)
            )
        }
        Text(text = "Note that a lower number means higher priority.")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Configure Bursts (CPU & I/O)")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.saveNewCard(
                    TaskCard(
                        id = null,
                        name = if (name.isEmpty()) "User's card" else name,
                        description = "New Task Card",
                        type = CardType.TASK,
                        burst = burstList,
                        priority = if (priority.isEmpty()) 1 else priority.toInt(),
                        arriveTime = 0
                    )
                )
                Toast.makeText(context, "Card Saved", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Save Card")
        }
    }
}


@Composable
fun BurstDialog(
    onDismiss: () -> Unit,
    burstList: MutableList<String>,
    updateBurstList: (MutableList<String>) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Configure Bursts") },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        updateBurstList(burstList.toMutableList().apply { add("cpu") })
                    }) {
                        Text("Add CPU")
                    }
                    Button(onClick = {
                        updateBurstList(burstList.toMutableList().apply { add("io") })
                    }) {
                        Text("Add IO")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (burstList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 210.dp)
                    ) {
                        itemsIndexed(burstList) { index, burst ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp)
                                    .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "${index + 1} - $burst",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                )
                                Button(onClick = {
                                    val newList = burstList.toMutableList().apply { removeAt(index) }
                                    updateBurstList(newList)
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No Burst",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}


