package com.groliks.tasksmanager.di.data.tasks

import com.groliks.tasksmanager.data.tasks.TasksRepository
import com.groliks.tasksmanager.data.tasks.TasksRepositoryImpl
import com.groliks.tasksmanager.data.tasks.TasksService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [TasksDataProvidesModule::class, TasksDataBindsModule::class])
interface TasksDataModule

@Module
class TasksDataProvidesModule {
    @Provides
    fun provideTasksService(retrofit: Retrofit): TasksService {
        return retrofit.create(TasksService::class.java)
    }
}

@Module
interface TasksDataBindsModule {
    @Binds
    fun bindTasksRepositoryImpl_to_TasksRepository(
        tasksRepositoryImpl: TasksRepositoryImpl
    ): TasksRepository
}