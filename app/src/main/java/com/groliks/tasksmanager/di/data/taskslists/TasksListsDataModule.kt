package com.groliks.tasksmanager.di.data.taskslists

import com.groliks.tasksmanager.data.taskslists.TasksListsRepository
import com.groliks.tasksmanager.data.taskslists.TasksListsRepositoryImpl
import com.groliks.tasksmanager.data.taskslists.TasksListsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [TasksListsDataProvidesModule::class, TasksListsDataBindsModule::class])
interface TasksListsDataModule

@Module
class TasksListsDataProvidesModule {
    @Provides
    fun provideTasksListsService(retrofit: Retrofit): TasksListsService {
        return retrofit.create(TasksListsService::class.java)
    }
}

@Module
interface TasksListsDataBindsModule {
    @Binds
    fun bindTasksListsRepositoryImpl_to_TasksListsRepository(
        tasksListsRepositoryImpl: TasksListsRepositoryImpl
    ): TasksListsRepository
}