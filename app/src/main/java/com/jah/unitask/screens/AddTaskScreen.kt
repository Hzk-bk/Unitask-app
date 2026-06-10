package com.jah.unitask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun AddTaskScreen(
    onSaveTask: (String, String, String) -> Unit,
    onBackClick: () -> Unit
){

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->

            deadline = "$dayOfMonth/${month + 1}/$year"

        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Add Task",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackClick
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))
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

        OutlinedButton(
            onClick = {
                datePickerDialog.show()
            }
        ) {
            Text(
                if (deadline.isBlank())
                    "Select Deadline"
                else
                    deadline
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                when {
                    title.isBlank() -> {
                        errorMessage = "Task title cannot be empty"
                    }

                    description.isBlank() -> {
                        errorMessage = "Description cannot be empty"
                    }

                    deadline.isBlank() -> {
                        errorMessage = "Deadline cannot be empty"
                    }

                    else -> {
                        errorMessage = ""

                        onSaveTask(
                            title,
                            description,
                            deadline
                        )
                    }
                }
            }
        ) {
            Text("Save Task")
        }
    }
}