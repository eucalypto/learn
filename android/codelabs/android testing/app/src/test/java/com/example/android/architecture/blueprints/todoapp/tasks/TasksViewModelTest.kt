package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class TasksViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeTestRepository: FakeTestRepository

    // The tested object
    private lateinit var viewModel: TasksViewModel

    @Before
    fun setUpViewModel() {
        fakeTestRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        fakeTestRepository.addTasks(task1, task2, task3)

        viewModel = TasksViewModel(fakeTestRepository)
    }

    @Test
    fun `addNewTask sets new task event`() {
        viewModel.addNewTask()

        val value = viewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun `setFilter ALL_TASKS tasks Add view is visible`() {
        viewModel.setFiltering(TasksFilterType.ALL_TASKS)

        val isVisible = viewModel.tasksAddViewVisible.getOrAwaitValue()

        assertThat(isVisible, `is`(true))
    }
}