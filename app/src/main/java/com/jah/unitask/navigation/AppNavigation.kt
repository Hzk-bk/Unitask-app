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
import com.jah.unitask.screens.SplashScreen
import com.jah.unitask.screens.WelcomeScreen
import com.jah.unitask.screens.StatisticsScreen
import androidx.compose.runtime.LaunchedEffect
import com.jah.unitask.screens.NotificationsScreen
import com.jah.unitask.data.NotificationRepository



@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authManager = FirebaseAuthManager()
    val taskRepository = TaskRepository()
    val context = LocalContext.current
    var selectedTask by remember {
        mutableStateOf<Task?>(null)
    }

    val startDestination = Screen.Splash.route
    val notificationRepository = NotificationRepository()

    var unreadNotifications by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {

        notificationRepository.getNotifications(
            onSuccess = { notifications ->

                unreadNotifications =
                    notifications.count {
                        !it.read
                    }
            },
            onFailure = {}
        )
    }


    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Splash.route) {

            SplashScreen(

                onSplashFinished = {

                    if (authManager.isUserLoggedIn()) {

                        navController.navigate(Screen.Tasks.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }

                    } else {

                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
        composable(Screen.Welcome.route) {

            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
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
                            Log.e("FIRESTORE_ERROR", it)
                        }
                    )
                },

                onBackClick = {
                    navController.popBackStack()
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
                onStatisticsClick = {
                    navController.navigate(Screen.Statistics.route)
                },

                onLogoutClick = {

                    authManager.logout()

                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                        onNotificationsClick = {
                    navController.navigate(Screen.Notifications.route)
                },
                unreadNotifications = unreadNotifications
            )
        }

        composable(Screen.Notifications.route) {

            NotificationsScreen(
                onBackClick = {
                    navController.popBackStack()
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

        composable(Screen.Statistics.route) {

            val repository = TaskRepository()

            var tasks by remember {
                mutableStateOf<List<Task>>(emptyList())
            }

            LaunchedEffect(Unit) {

                repository.getTasks(
                    onSuccess = {
                        tasks = it
                    },
                    onFailure = {}
                )
            }

            StatisticsScreen(
                totalTasks = tasks.size,
                openTasks = tasks.count { it.status == "Open" },
                completedTasks = tasks.count { it.status == "Completed" },

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        }

    }

