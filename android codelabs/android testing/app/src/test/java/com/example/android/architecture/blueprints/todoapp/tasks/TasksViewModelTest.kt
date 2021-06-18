package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TasksViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `addNewTask sets new task event`() {
        val viewModel =
            TasksViewModel(ApplicationProvider.getApplicationContext())

        viewModel.addNewTask()

        val value = viewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun `setFilter ALL_TASKS tasks Add view is visible`() {

        val viewModel =
            TasksViewModel(ApplicationProvider.getApplicationContext())

        viewModel.setFiltering(TasksFilterType.ALL_TASKS)

        val isVisible = viewModel.tasksAddViewVisible.getOrAwaitValue()

        assertThat(isVisible, `is`(true))
    }
}