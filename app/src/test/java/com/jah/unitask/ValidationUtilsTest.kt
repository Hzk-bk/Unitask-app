package com.jah.unitask

import com.jah.unitask.utils.ValidationUtils
import org.junit.Assert.*
import org.junit.Test

class ValidationUtilsTest {

    @Test
    fun emptyTitleReturnsFalse() {

        assertFalse(
            ValidationUtils.isTitleValid("")
        )
    }

    @Test
    fun validTitleReturnsTrue() {

        assertTrue(
            ValidationUtils.isTitleValid("Assignment")
        )
    }

    @Test
    fun emptyDescriptionReturnsFalse() {

        assertFalse(
            ValidationUtils.isDescriptionValid("")
        )
    }

    @Test
    fun emptyDeadlineReturnsFalse() {

        assertFalse(
            ValidationUtils.isDeadlineValid("")
        )
    }

    @Test
    fun validDeadlineReturnsTrue() {

        assertTrue(
            ValidationUtils.isDeadlineValid("10/06/2026")
        )
    }
}