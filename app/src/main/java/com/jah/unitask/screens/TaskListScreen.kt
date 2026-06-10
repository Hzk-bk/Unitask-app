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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem



@Composable
fun TaskListScreen(
    onAddTaskClick: () -> Unit,
    onEditTaskClick: (Task) -> Unit,
    onLogoutClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    unreadNotifications: Int
) {

    val repository = TaskRepository()

    var tasks by remember {
        mutableStateOf<List<Task>>(emptyList())
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var taskToDelete by remember {
        mutableStateOf<Task?>(null)
    }

    var menuExpanded by remember {
        mutableStateOf(false)
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

    val filteredTasks = tasks.filter {

        it.title.contains(
            searchText,
            ignoreCase = true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {


        Box {

            OutlinedButton(
                onClick = {
                    menuExpanded = true
                }
            ) {
                Text("Menu")
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = {
                    menuExpanded = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text("Notifications ($unreadNotifications)")
                    },
                    onClick = {
                        menuExpanded = false
                        onNotificationsClick()
                    }
                )

                DropdownMenuItem(
                    text = { Text("Statistics") },
                    onClick = {
                        menuExpanded = false
                        onStatisticsClick()
                    }
                )

                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        menuExpanded = false
                        onLogoutClick()
                    }
                )
            }
        }



        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = onAddTaskClick
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = {
                Text("Search Tasks")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredTasks.isEmpty()) {

            Column {

                Text(
                    text = "📋 No tasks yet",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap Add Task to create your first task."
                )
            }

        } else {

            LazyColumn {

                items(filteredTasks) { task ->

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
                                text = "Status: ${task.status}",
                                color =
                                    if (task.status == "Completed")
                                        androidx.compose.ui.graphics.Color(0xFF4CAF50)
                                    else
                                        androidx.compose.ui.graphics.Color(0xFFFF9800)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {

                                    val updatedTask = task.copy(
                                        status = if (task.status == "Open")
                                            "Completed"
                                        else
                                            "Open"
                                    )

                                    repository.updateTask(
                                        task = updatedTask,
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
                                Text(
                                    if (task.status == "Open")
                                        "Mark Completed"
                                    else
                                        "Mark Open"
                                )
                            }

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
                                    taskToDelete = task
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }

            }

            if (taskToDelete != null) {

                AlertDialog(
                    onDismissRequest = {
                        taskToDelete = null
                    },

                    title = {
                        Text("Delete Task")
                    },

                    text = {
                        Text("Are you sure you want to delete this task?")
                    },

                    confirmButton = {

                        Button(
                            onClick = {

                                repository.deleteTask(
                                    taskId = taskToDelete!!.id,

                                    onSuccess = {

                                        repository.getTasks(
                                            onSuccess = {
                                                tasks = it
                                            },
                                            onFailure = {}
                                        )

                                        taskToDelete = null
                                    },

                                    onFailure = {
                                        taskToDelete = null
                                    }
                                )
                            }
                        ) {
                            Text("Delete")
                        }
                    },

                    dismissButton = {

                        OutlinedButton(
                            onClick = {
                                taskToDelete = null
                            }
                        ) {
                            Text("Cancel")
                        }

        })
    }
}
    }
}
