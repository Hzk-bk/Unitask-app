package com.jah.unitask

import org.junit.Assert.*
import org.junit.Test

class TaskLogicTest {

    @Test
    fun searchFindsMatchingTask() {

        val tasks = listOf(
            "Math Assignment",
            "Physics Lab",
            "Project Report"
        )

        val result = tasks.filter {
            it.contains("Math", ignoreCase = true)
        }

        assertEquals(1, result.size)
    }

    @Test
    fun searchReturnsEmptyWhenNoMatch() {

        val tasks = listOf(
            "Math Assignment",
            "Physics Lab"
        )

        val result = tasks.filter {
            it.contains("Biology", ignoreCase = true)
        }

        assertTrue(result.isEmpty())
    }

    @Test
    fun taskStatusChangesToCompleted() {

        var status = "Open"

        status = "Completed"

        assertEquals("Completed", status)
    }
}