package com.esraa.nayel.madarsofttask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esraa.nayel.madarsofttask.domain.usecase.GetAllUsersUseCase
import com.esraa.nayel.madarsofttask.domain.usecase.InsertUserUseCase
import com.esraa.nayel.madarsofttask.ui.screens.display.DisplayScreenState
import com.esraa.nayel.madarsofttask.ui.screens.input.InputScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val insertUserUseCase: InsertUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _inputState = MutableStateFlow(InputScreenState())
    val inputState: StateFlow<InputScreenState> = _inputState.asStateFlow()

    private val _displayState = MutableStateFlow(DisplayScreenState())
    val displayState: StateFlow<DisplayScreenState> = _displayState.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getAllUsersUseCase().collect { users ->
                _displayState.value = _displayState.value.copy(
                    users = users,
                    isLoading = false
                )
            }
        }
    }

    fun insertUser(name: String, age: String, jobTitle: String, gender: String) {
        viewModelScope.launch {
            _inputState.value = _inputState.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            val ageInt = age.toIntOrNull()
            if (ageInt == null) {
                _inputState.value = _inputState.value.copy(
                    isLoading = false,
                    errorMessage = "Please enter a valid age"
                )
                return@launch
            }

            insertUserUseCase(name, ageInt, jobTitle, gender)
                .onSuccess {
                    _inputState.value = _inputState.value.copy(
                        isLoading = false,
                        successMessage = "User saved successfully!"
                    )
                }
                .onFailure { exception ->
                    _inputState.value = _inputState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
        }
    }

    fun clearMessages() {
        _inputState.value = _inputState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}