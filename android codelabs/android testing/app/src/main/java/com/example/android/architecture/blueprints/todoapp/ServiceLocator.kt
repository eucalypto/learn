package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private var database: ToDoDatabase? = null

    @Volatile
    var tasksRepository: TasksRepository? = null
        @VisibleForTesting set

    private val lock = Any()

    fun provideTasksRepository(context: Context)
            : TasksRepository = synchronized(this) {
        return tasksRepository ?: createTasksRepository(context)

    }

    private fun createTasksRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(
            TasksRemoteDataSource,
            createTaskLocalDataSource(context)
        )
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): TasksDataSource {
        val database = database ?: createDatabase(context)
        val taskDao = database.taskDao()
        return TasksLocalDataSource(taskDao)
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        val database = Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()

        this.database = database
        return database
    }

    @VisibleForTesting
    fun resetRepository() = synchronized(lock) {
        runBlocking {
            TasksRemoteDataSource.deleteAllTasks()
        }

        database?.apply {
            clearAllTables()
            close()
        }

        database = null
        tasksRepository = null
    }

}