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

    fun getTasks(
        onSuccess: (List<Task>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        tasksCollection
            .get()
            .addOnSuccessListener { result ->

                val tasks = result.documents.map { document ->

                    Task(
                        id = document.id,
                        title = document.getString("title") ?: "",
                        description = document.getString("description") ?: "",
                        deadline = document.getString("deadline") ?: "",
                        status = document.getString("status") ?: "Open",
                        userId = document.getString("userId") ?: ""
                    )
                }

                onSuccess(tasks)
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Failed to load tasks")
            }
    }

    fun updateTask(
        task: Task,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        tasksCollection
            .document(task.id)
            .set(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Failed to update task")
            }
    }

    fun deleteTask(
        taskId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        tasksCollection
            .document(taskId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Failed to delete task")
            }
    }

}