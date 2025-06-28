package com.esraa.nayel.madarsofttask.ui

import androidx.activity.ComponentActivity
import com.esraa.nayel.madarsofttask.data.UserDatabase
import com.esraa.nayel.madarsofttask.data.UserRepositoryImpl
import com.esraa.nayel.madarsofttask.domain.usecase.GetAllUsersUseCase
import com.esraa.nayel.madarsofttask.domain.usecase.InsertUserUseCase
import com.esraa.nayel.madarsofttask.domain.repository.UserRepository
import com.esraa.nayel.madarsofttask.ui.viewmodel.UserViewModelFactory

class DependencyProvider(private val context: ComponentActivity) {

    private val database by lazy { UserDatabase.getDatabase(context) }
    private val repository: UserRepository by lazy { UserRepositoryImpl(database.userDao()) }

    private val insertUserUseCase by lazy { InsertUserUseCase(repository) }
    private val getAllUsersUseCase by lazy { GetAllUsersUseCase(repository) }

    fun provideViewModelFactory(): UserViewModelFactory {
        return UserViewModelFactory(insertUserUseCase, getAllUsersUseCase)
    }
}