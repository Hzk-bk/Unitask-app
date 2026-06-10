package com.jah.unitask.utils

object ValidationUtils {

    fun isTitleValid(title: String): Boolean {
        return title.isNotBlank()
    }

    fun isDescriptionValid(description: String): Boolean {
        return description.isNotBlank()
    }

    fun isDeadlineValid(deadline: String): Boolean {
        return deadline.isNotBlank()
    }
}