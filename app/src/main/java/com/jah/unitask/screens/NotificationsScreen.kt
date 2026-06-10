package com.jah.unitask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jah.unitask.data.Notification
import com.jah.unitask.data.NotificationRepository

@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit
) {

    val repository = NotificationRepository()

    var notifications by remember {
        mutableStateOf<List<Notification>>(emptyList())
    }

    LaunchedEffect(Unit) {

        repository.getNotifications(
            onSuccess = {
                notifications = it
            },
            onFailure = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClick
        ) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(notifications) { notification ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(notification.message)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            if (notification.read)
                                "Read"
                            else
                                "Unread"
                        )

                        if (!notification.read) {

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = {

                                    repository.markAsRead(
                                        notification.id,
                                        onSuccess = {

                                            repository.getNotifications(
                                                onSuccess = {
                                                    notifications = it
                                                },
                                                onFailure = {}
                                            )
                                        },
                                        onFailure = {}
                                    )
                                }
                            ) {
                                Text("Mark Read")
                            }
                        }
                    }
                }
            }
        }
    }
}