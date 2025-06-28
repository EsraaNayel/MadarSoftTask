package com.esraa.nayel.madarsofttask.ui.screens.display

import com.esraa.nayel.madarsofttask.domain.models.User

data class DisplayScreenState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
