package com.esraa.nayel.madarsofttask.domain.repository

import com.esraa.nayel.madarsofttask.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User): Result<Unit>
    fun getAllUsers(): Flow<List<User>>
}