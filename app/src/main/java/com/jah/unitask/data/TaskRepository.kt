package com.jah.unitask.data

import com.google.firebase.firestore.FirebaseFirestore

class TaskRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val tasksCollection =
        firestore.collection("tasks")

    fun addTask(
        task: Task,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        tasksCollection
            .add(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Failed to add task")
            }
    }
}