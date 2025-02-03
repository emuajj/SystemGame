package com.joanjaume.myapplication.android.components.ui.Selector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

@Composable
fun SelectorComposable(
    options: Map<String, Int>,
    label: String = "Choose an option",
    onOptionSelected: (key: String, value: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.keys.first()) }

    Column(modifier = Modifier.padding(8.dp)) {  // Reduced padding for more compact layout
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, fontSize = 12.sp) },  // Smaller label font size
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),  // Smaller text size for options
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = Color.Gray
            ),
            singleLine = true  // Ensures the text field does not expand vertically
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (key, value) ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = key
                        expanded = false
                        onOptionSelected(key, value)
                    }
                ) {
                    Text(text = key, fontSize = 14.sp)  // Smaller text size for dropdown items
                }
            }
        }
    }
}
