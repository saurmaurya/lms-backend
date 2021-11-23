package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
    fun findUserByEmail(username: String): User?
    fun existsUserByEmail(email: String): Boolean
}