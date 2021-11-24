package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.enums.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
    fun findUserByEmail(username: String): User?
    fun existsUserByEmail(email: String): Boolean
    fun findAllByRolesAndIsActiveTrue(roles: Set<Role>, pageable: Pageable): Page<User>
}