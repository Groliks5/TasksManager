package com.groliks.tasksmanager.di

import android.content.Context
import com.groliks.tasksmanager.di.data.DataModule
import com.groliks.tasksmanager.view.authentication.AuthenticationFragment
import com.groliks.tasksmanager.view.tasklist.TaskListFragment
import com.groliks.tasksmanager.view.taskslists.TasksListsFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(fragment: AuthenticationFragment)
    fun inject(fragment: TasksListsFragment)
    fun inject(fragment: TaskListFragment)
}