package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.LoginDto
import com.srsdev.tech.lms.models.dtos.UserRegistrationDto
import com.srsdev.tech.lms.models.enums.Role
import com.srsdev.tech.lms.models.payload.response.JwtResponse

interface AuthService {
    fun register(userBody: UserRegistrationDto, roles: Set<Role>): User
    fun login(loginDto: LoginDto): JwtResponse
    fun getUser(userId: String): User
}