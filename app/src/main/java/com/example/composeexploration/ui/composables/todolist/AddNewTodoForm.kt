package com.example.composeexploration.ui.composables.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddNewTodoForm(
    onSubmitted: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        var title by remember { mutableStateOf("") }

        Text(
            text = "What Will You Do?",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(bottom = 16.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Task") }
        )
        ElevatedButton(
            modifier = Modifier.align(Alignment.End),
            onClick =  { onSubmitted(title) },
            colors = ButtonColors(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.primary,
                Color.LightGray,
                Color.LightGray
            )
        ) {
            Text(
                text = "Submit",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}