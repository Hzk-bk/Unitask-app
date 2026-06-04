package com.jah.unitask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jah.unitask.auth.FirebaseAuthManager
import com.jah.unitask.screens.LoginScreen
import com.jah.unitask.screens.RegisterScreen
import com.jah.unitask.screens.TaskListScreen
import com.jah.unitask.data.Task
import com.jah.unitask.data.TaskRepository
import com.jah.unitask.screens.AddTaskScreen
import android.util.Log
import com.jah.unitask.screens.EditTaskScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authManager = FirebaseAuthManager()
    val taskRepository = TaskRepository()
    var selectedTask by remember {
        mutableStateOf<Task?>(null)
    }


    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {

            LoginScreen(
                onLoginClick = { email, password ->

                    authManager.login(
                        email = email,
                        password = password,
                        onSuccess = {
                            navController.navigate(Screen.Tasks.route)
                        },
                        onFailure = {
                            println(it)
                        }
                    )
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {

            RegisterScreen(
                onRegisterClick = { email, password ->

                    authManager.register(
                        email = email,
                        password = password,
                        onSuccess = {
                            navController.navigate(Screen.Tasks.route)
                        },
                        onFailure = {
                            println(it)
                        }
                    )
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.AddTask.route) {

            AddTaskScreen(
                onSaveTask = { title, description, deadline ->

                    val task = Task(
                        title = title,
                        description = description,
                        deadline = deadline
                    )

                    taskRepository.addTask(
                        task = task,
                        onSuccess = {
                            navController.popBackStack()
                        },
                        onFailure = {
                            android.util.Log.e("FIRESTORE_ERROR", it)
                        }
                    )
                }
            )
        }

        composable(Screen.Tasks.route) {

            TaskListScreen(
                onAddTaskClick = {
                    navController.navigate(Screen.AddTask.route)
                },
                onEditTaskClick = { task ->

                    selectedTask = task

                    navController.navigate(Screen.EditTask.route)

                }
            )
        }

        composable(Screen.EditTask.route) {

            selectedTask?.let { task ->

                EditTaskScreen(
                    currentTitle = task.title,
                    currentDescription = task.description,
                    currentDeadline = task.deadline,

                    onUpdateTask = { title, description, deadline ->

                        val updatedTask = task.copy(
                            title = title,
                            description = description,
                            deadline = deadline
                        )

                        taskRepository.updateTask(
                            task = updatedTask,
                            onSuccess = {
                                navController.popBackStack()
                            },
                            onFailure = {
                                Log.e("UPDATE_ERROR", it)
                            }
                        )
                    },

                    onCancel = {
                        navController.popBackStack()
                    }
                )

            }
        }

        }
    }
