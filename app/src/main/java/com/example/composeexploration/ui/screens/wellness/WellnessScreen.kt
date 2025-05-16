package com.example.composeexploration.ui.screens.wellness

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeexploration.ui.composables.wellnesstask.WellnessTaskList
import com.example.composeexploration.ui.theme.ComposeExplorationTheme
import com.example.composeexploration.viewmodel.WellnessViewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        val list = remember { wellnessViewModel.tasks }
        WellnessTaskList(
            list = list,
            onCloseTask = { task -> wellnessViewModel.removeItem(task) },
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            }
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewWaterCounter() {
    ComposeExplorationTheme {
        WellnessScreen()
    }
}