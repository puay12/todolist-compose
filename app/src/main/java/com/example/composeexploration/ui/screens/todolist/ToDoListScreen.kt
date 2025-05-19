package com.example.composeexploration.ui.screens.todolist

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeexploration.data.todolist.ToDoListItem
import com.example.composeexploration.ui.composables.todolist.AddTodoDialog
import com.example.composeexploration.ui.composables.todolist.TodoItemHolder
import com.example.composeexploration.ui.theme.ComposeExplorationTheme
import com.example.composeexploration.viewmodel.ToDoListViewModel

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ToDoListScreen(
    modifier: Modifier = Modifier,
    toDoListViewModel: ToDoListViewModel = viewModel()
) {
    var openDialog by remember { mutableStateOf(false) }
    val transitionState = remember { MutableTransitionState(false) }
    val dialogTransition = updateTransition(targetState = transitionState, label = "dialogTransition")
    val scale by dialogTransition.animateFloat(
        label = "scale",
        transitionSpec = { tween(300) }
    ) { if (openDialog) 1f else 0.8f }
    val alpha by dialogTransition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(300) }
    ) { if (openDialog) 1f else 0f }

    val snackbarHostState = remember { SnackbarHostState() }

    if (openDialog) {
        AddTodoDialog(
            modifier = Modifier.scale(scale).graphicsLayer(alpha),
            onDismissed = { openDialog = false },
            onSubmitted = { title ->
                toDoListViewModel.addTodoItem(
                    ToDoListItem (
                        id = toDoListViewModel.generateNewId(),
                        title = title
                    )
                )
                openDialog = false
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Add new todo")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val lists = toDoListViewModel.todoListItems.collectAsState().value
            var size by remember { mutableIntStateOf(lists.size) }

            LaunchedEffect(key1 = lists.size) {
                if (lists.size > size) {
                    snackbarHostState.showSnackbar(
                        message = "New item is added",
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Short
                    )
                } else if (lists.size < size) {
                    snackbarHostState.showSnackbar(
                        message = "Item is deleted",
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Short
                    )
                }
                size = lists.size
            }

            TodoScreenTitle()
            Spacer(modifier = modifier.height(24.dp))
            ToListContents(
                listItem = lists,
                onCheckedChange = { todo, checked -> toDoListViewModel.onTodoChecked(todo, checked) },
                onRemove = { todo -> toDoListViewModel.removeTodoItem(todo) }
            )
        }
    }
}

@Composable
fun TodoScreenTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        text = "Todo List",
        style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToListContents(
    listItem: List<ToDoListItem>,
    onCheckedChange: (ToDoListItem, Boolean) -> Unit,
    onRemove: (ToDoListItem) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(
            items = listItem,
            key = { item -> item.id }
        ) { todo ->
            TodoItemHolder(
                modifier = Modifier.animateItem(
                    fadeInSpec = tween(durationMillis = 250),
                    fadeOutSpec = tween(durationMillis = 100),
                    placementSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy)
                ),
                title = todo.title,
                isChecked = todo.isCompleted,
                onCheckedChange = { checked -> onCheckedChange(todo, checked) },
                onRemove = { onRemove(todo) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTodoScreen() {
    ComposeExplorationTheme {
        ToDoListScreen()
    }
}