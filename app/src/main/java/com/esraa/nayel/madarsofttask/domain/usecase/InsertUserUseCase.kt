package com.esraa.nayel.madarsofttask.domain.usecase

import com.esraa.nayel.madarsofttask.domain.models.User
import com.esraa.nayel.madarsofttask.domain.repository.UserRepository

class InsertUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(
        name: String,
        age: Int,
        jobTitle: String,
        gender: String
    ): Result<Unit> {
        return when {
            name.isBlank() -> Result.failure(Exception("Name cannot be empty"))
            age <= 0 -> Result.failure(Exception("Age must be positive"))
            jobTitle.isBlank() -> Result.failure(Exception("Job title cannot be empty"))
            gender.isBlank() -> Result.failure(Exception("Gender must be selected"))
            else -> {
                val user = User(
                    name = name.trim(),
                    age = age,
                    jobTitle = jobTitle.trim(),
                    gender = gender
                )
                repository.insertUser(user)
            }
        }
    }
}