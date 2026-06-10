package com.jah.unitask.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash")

    object Welcome : Screen("welcome")

    object Login : Screen("login")

    object Register : Screen("register")

    object Tasks : Screen("tasks")

    object AddTask : Screen("add_task")

    object EditTask : Screen("edit_task")

    object Statistics : Screen("statistics")

    object Notifications : Screen("notifications")
}