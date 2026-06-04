package com.jah.unitask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jah.unitask.data.Task
import com.jah.unitask.data.TaskRepository
import androidx.compose.material3.OutlinedButton

@Composable
fun TaskListScreen(
    onAddTaskClick: () -> Unit,
    onEditTaskClick: (Task) -> Unit
)
  {

    val repository = TaskRepository()

    var tasks by remember {
        mutableStateOf<List<Task>>(emptyList())
    }

    LaunchedEffect(Unit) {

        repository.getTasks(
            onSuccess = {
                tasks = it
            },
            onFailure = {

            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddTaskClick
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {

            Text("No tasks yet")

        } else {

            LazyColumn {

                items(tasks) { task ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = task.description
                            )

                            Text(
                                text = "Deadline: ${task.deadline}"
                            )

                            Text(
                                text = "Status: ${task.status}"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {
                                    onEditTaskClick(task)
                                }
                            ) {
                                Text("Edit")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {

                                    repository.deleteTask(
                                        taskId = task.id,
                                        onSuccess = {

                                            repository.getTasks(
                                                onSuccess = {
                                                    tasks = it
                                                },
                                                onFailure = {

                                                }
                                            )
                                        },
                                        onFailure = {

                                        }
                                    )
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}