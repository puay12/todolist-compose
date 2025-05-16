package com.example.composeexploration.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.composeexploration.data.wellnesstask.WellnessTask

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask> get() = _tasks

    fun removeItem(item: WellnessTask) {
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        _tasks.find { it.id == item.id }?.let { task ->
            task.isChecked = checked
        }
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }