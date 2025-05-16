package com.example.composeexploration.data.wellnesstask

data class WellnessTask(
    val id: Int,
    val label: String,
    var isChecked: Boolean = false
)
