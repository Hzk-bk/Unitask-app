package com.jah.unitask.data

import com.google.firebase.firestore.FirebaseFirestore

class NotificationRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getNotifications(
        onSuccess: (List<Notification>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("notifications")
            .get()
            .addOnSuccessListener { result ->

                val notifications = result.documents.map {

                    Notification(
                        id = it.id,
                        message = it.getString("message") ?: "",
                        read = it.getBoolean("read") ?: false
                    )
                }

                onSuccess(notifications)
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Error")
            }
    }

    fun markAsRead(
        notificationId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        db.collection("notifications")
            .document(notificationId)
            .update("read", true)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Error")
            }
    }
}