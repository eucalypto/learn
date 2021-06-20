package com.example.android.architecture.blueprints.todoapp.taskdetail

import FakeAndroidTestRepository
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {

    lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanUpRepository() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetails_displayedInUi() = runBlockingTest {

        val activeTask = Task(
            "Active Task", "AndroidX Rocks", false
        )
        repository.saveTask(activeTask)

        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.task_detail_title_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text))
            .check(matches(withText("Active Task")))
        onView(withId(R.id.task_detail_description_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text))
            .check(matches(withText("AndroidX Rocks")))
        onView(withId(R.id.task_detail_complete_checkbox))
            .check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_complete_checkbox))
            .check(matches(not(isChecked())))
    }

    @Test
    fun completedTaskDetails_DisplayedInUi() = runBlockingTest {
        // GIVEN - Add completed task to the DB
        val task = Task(
            "Destroy the Ring",
            "Bring the Ring to Mount Doom and toss it in the fire",
            true
        )
        repository.saveTask(task)

        // WHEN - Details fragment launched to display task
        val bundle = TaskDetailFragmentArgs(task.id).toBundle()
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        // THEN - Task details are displayed on the screen
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.task_detail_title_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_title_text))
            .check(matches(withText("Destroy the Ring")))

        onView(withId(R.id.task_detail_description_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.task_detail_description_text))
            .check(matches(withText("Bring the Ring to Mount Doom and toss it in the fire")))

        onView(withId(R.id.task_detail_complete_checkbox))
            .check(matches(isChecked()))
        onView(withId(R.id.task_detail_complete_checkbox))
            .check(matches(isDisplayed()))
    }

}