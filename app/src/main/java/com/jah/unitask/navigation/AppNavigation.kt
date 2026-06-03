package com.jah.unitask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jah.unitask.auth.FirebaseAuthManager
import com.jah.unitask.screens.LoginScreen
import com.jah.unitask.screens.RegisterScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val authManager = FirebaseAuthManager()

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
                            println("LOGIN SUCCESS")
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
                            navController.navigate(Screen.Login.route)
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
    }
}
