package com.groliks.tasksmanager.di.data.authentication

import com.groliks.tasksmanager.data.authenticaton.AuthenticationRepository
import com.groliks.tasksmanager.data.authenticaton.AuthenticationRepositoryImpl
import com.groliks.tasksmanager.data.authenticaton.AuthenticationService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [AuthenticationDataProvidesModule::class, AuthenticationDataBindsModule::class])
interface AuthenticationDataModule

@Module
class AuthenticationDataProvidesModule {
    @Provides
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }
}

@Module
interface AuthenticationDataBindsModule {
    @Binds
    fun bindAuthenticationRepositoryImpl_to_AuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}