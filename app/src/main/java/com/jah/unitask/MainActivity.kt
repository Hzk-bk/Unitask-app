package com.jah.unitask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.jah.unitask.navigation.AppNavigation
import com.jah.unitask.ui.theme.UnitaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        Log.d("FirebaseCheck", "Firebase initialized successfully")

        enableEdgeToEdge()

        setContent {
            UnitaskTheme {
                AppNavigation()
            }
        }
    }
}