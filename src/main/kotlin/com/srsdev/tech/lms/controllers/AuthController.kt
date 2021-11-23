package com.srsdev.tech.lms.controllers

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.LoginDto
import com.srsdev.tech.lms.models.dtos.UserRegistrationDto
import com.srsdev.tech.lms.models.payload.response.JwtResponse
import com.srsdev.tech.lms.services.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody body: UserRegistrationDto): User {
        return authService.register(body)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody body: LoginDto): JwtResponse{
        return authService.login(body)
    }
}