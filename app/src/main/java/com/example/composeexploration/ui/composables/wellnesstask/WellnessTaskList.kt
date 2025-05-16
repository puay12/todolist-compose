package com.example.composeexploration.ui.composables.wellnesstask

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composeexploration.data.wellnesstask.WellnessTask

@Composable
fun WellnessTaskList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCloseTask: (WellnessTask) -> Unit,
    onCheckedTask: (WellnessTask, Boolean) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                isChecked = task.isChecked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                onClose = { onCloseTask(task) }
            )
        }
    }
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}