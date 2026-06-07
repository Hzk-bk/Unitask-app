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
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext



@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authManager = FirebaseAuthManager()
    val taskRepository = TaskRepository()
    val context = LocalContext.current
    var selectedTask by remember {
        mutableStateOf<Task?>(null)
    }


    val startDestination = if (authManager.isUserLoggedIn()) {
        Screen.Tasks.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
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

                            Toast.makeText(
                                context,
                                "Invalid email or password",
                                Toast.LENGTH_SHORT
                            ).show()

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

                            Toast.makeText(
                                context,
                                it,
                                Toast.LENGTH_SHORT
                            ).show()

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
                },
                onLogoutClick = {

                    authManager.logout()

                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
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
