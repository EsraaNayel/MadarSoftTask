package com.esraa.nayel.madarsofttask.domain.usecase

import com.esraa.nayel.madarsofttask.domain.models.User
import com.esraa.nayel.madarsofttask.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getAllUsers()
    }
}