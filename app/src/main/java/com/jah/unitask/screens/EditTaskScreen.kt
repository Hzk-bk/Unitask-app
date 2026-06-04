package com.jah.unitask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditTaskScreen(
    currentTitle: String,
    currentDescription: String,
    currentDeadline: String,
    onUpdateTask: (String, String, String) -> Unit
) {

    var title by remember { mutableStateOf(currentTitle) }
    var description by remember { mutableStateOf(currentDescription) }
    var deadline by remember { mutableStateOf(currentDeadline) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Edit Task",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = deadline,
            onValueChange = { deadline = it },
            label = { Text("Deadline") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onUpdateTask(
                    title,
                    description,
                    deadline
                )
            }
        ) {
            Text("Update Task")
        }
    }
}