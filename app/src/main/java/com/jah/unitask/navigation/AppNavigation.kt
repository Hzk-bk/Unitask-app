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

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authManager = FirebaseAuthManager()
    val taskRepository = TaskRepository()

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
                }
            )
        }

        }
    }
