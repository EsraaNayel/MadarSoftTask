package com.esraa.nayel.madarsofttask.data

import com.esraa.nayel.madarsofttask.domain.models.User
import com.esraa.nayel.madarsofttask.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User): Result<Unit> {
        return try {
            userDao.insertUser(user.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { userEntities ->
            userEntities.map { it.toDomain() }
        }
    }

}