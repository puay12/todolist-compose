package com.example.composeexploration.viewmodel

import androidx.lifecycle.ViewModel
import com.example.composeexploration.data.todolist.ToDoListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ToDoListViewModel : ViewModel() {
    private var _todoListItems: MutableStateFlow<MutableList<ToDoListItem>> =
        MutableStateFlow(mutableListOf())
    val todoListItems: StateFlow<List<ToDoListItem>> get() = _todoListItems


    fun addTodoItem(todoItem: ToDoListItem) {
        _todoListItems.value = (_todoListItems.value + todoItem).toMutableList()
    }

    fun removeTodoItem(todoItem: ToDoListItem) {
        _todoListItems.value = _todoListItems.value.filter { it != todoItem }.toMutableList()
    }

    fun onTodoChecked(todo: ToDoListItem, checked: Boolean) {
        _todoListItems.value = _todoListItems.value.map {
            if (it.id == todo.id) it.copy(isCompleted = checked) else it
        }.toMutableList()
    }

    fun generateNewId() : String {
        var result = getRandomString()
        var isExisted = (_todoListItems.value.size) > 0

        while (isExisted) {
            _todoListItems.value.find { it.id == result }?.let {
                result = getRandomString()
                isExisted = true
            } ?: run {
                isExisted = false
            }
        }

        return result
    }

    private fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return String(CharArray(15) { allowedChars.random() })
    }
}