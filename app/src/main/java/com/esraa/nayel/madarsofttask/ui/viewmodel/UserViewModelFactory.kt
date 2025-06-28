package com.esraa.nayel.madarsofttask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esraa.nayel.madarsofttask.domain.usecase.GetAllUsersUseCase
import com.esraa.nayel.madarsofttask.domain.usecase.InsertUserUseCase

class UserViewModelFactory(
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(insertUserUseCase, getAllUsersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}