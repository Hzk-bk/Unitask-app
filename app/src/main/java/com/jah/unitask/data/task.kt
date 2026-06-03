package com.jah.unitask.data

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val deadline: String = "",
    val status: String = "Open",
    val userId: String = ""
)
