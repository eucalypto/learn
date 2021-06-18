package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class StatisticsUtilsKtTest {

    @Nested
    inner class getActiveAndCompletedStats {
        @Test
        fun `no completed returns hundred zero`() {
            val tasks = listOf(Task("title", "descr", isCompleted = false))

            val result = getActiveAndCompletedStats(tasks)

            assertThat(result.completedTasksPercent, `is`(0f))
            assertThat(result.activeTasksPercent, `is`(100f))
        }

        @Test
        fun `when all completed returns zero hundred`() {
            val tasks = listOf(Task(isCompleted = true))

            val result = getActiveAndCompletedStats(tasks)

            assertThat(result.activeTasksPercent, `is`(0f))
            assertThat(result.completedTasksPercent, `is`(100f))
        }

        @Test
        fun `2 completed 3 active returns 40 60`() {
            val tasks = listOf(
                Task(isCompleted = true),
                Task(isCompleted = true),
                Task(isCompleted = false),
                Task(isCompleted = false),
                Task(isCompleted = false)
            )

            val result = getActiveAndCompletedStats(tasks)

            assertThat(result.completedTasksPercent, `is`(40f))
            assertThat(result.activeTasksPercent, `is`(60f))
        }

        @Test
        fun `null input returns zeros`() {
            val tasks: List<Task>? = null

            val result = getActiveAndCompletedStats(tasks)

            assertThat(result.activeTasksPercent, `is`(0f))
            assertThat(result.completedTasksPercent, `is`(0f))
        }

        @Test
        fun `empty list returns zeros`() {
            val tasks: List<Task> = emptyList()

            val result = getActiveAndCompletedStats(tasks)

            assertThat(result.completedTasksPercent, `is`(0f))
            assertThat(result.activeTasksPercent, `is`(0f))
        }
    }
}