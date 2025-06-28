package com.esraa.nayel.madarsofttask.data

import com.esraa.nayel.madarsofttask.domain.models.User


    fun UserEntity.toDomain(): User {
        return User(
            id = id,
            name = name,
            age = age,
            jobTitle = jobTitle,
            gender = gender
        )
    }

    fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            age = age,
            jobTitle = jobTitle,
            gender = gender
        )
    }

