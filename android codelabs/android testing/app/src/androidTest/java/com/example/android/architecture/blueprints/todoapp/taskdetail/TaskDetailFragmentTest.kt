package com.example.android.architecture.blueprints.todoapp.taskdetail

import FakeAndroidTestRepository
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUpRepository() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun activeTaskDetails_displayedInUi() = runBlockingTest {

        val activeTask = Task(
            "Active Task", "AndroidX rocks", false
        )
        repository.saveTask(activeTask)

        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)
    }
}