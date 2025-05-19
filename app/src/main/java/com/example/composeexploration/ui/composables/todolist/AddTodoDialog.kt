package com.example.composeexploration.ui.composables.todolist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTodoDialog(
    modifier: Modifier = Modifier,
    onSubmitted: (String) -> Unit,
    onDismissed: () -> Unit
) {
    Dialog(onDismissRequest = onDismissed) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            AddNewTodoForm(onSubmitted)
        }
    }
}