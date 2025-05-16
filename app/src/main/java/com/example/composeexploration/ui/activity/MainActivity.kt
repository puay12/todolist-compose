package com.example.composeexploration.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composeexploration.ui.screens.todolist.ToDoListScreen
import com.example.composeexploration.ui.theme.ComposeExplorationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeExplorationTheme {
                ToDoListScreen()
            }
        }
    }
}