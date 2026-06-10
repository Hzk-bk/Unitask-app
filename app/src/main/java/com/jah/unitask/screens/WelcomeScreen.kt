package com.jah.unitask.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp


@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit
) {

    val alphaAnimation = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alphaAnimation.value),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "📚 UniTask",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Organize your university tasks and deadlines."
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onGetStartedClick
        ) {
            Text("Get Started")
        }
    }
}