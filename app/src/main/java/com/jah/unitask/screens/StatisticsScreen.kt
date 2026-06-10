package com.jah.unitask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen(
    totalTasks: Int,
    openTasks: Int,
    completedTasks: Int,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Total Tasks: $totalTasks")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Open Tasks: $openTasks")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Completed Tasks: $completedTasks")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick
        ) {
            Text("Back")
        }
    }
}