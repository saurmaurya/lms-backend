package com.srsdev.tech.lms.controllers

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.LoginDto
import com.srsdev.tech.lms.models.dtos.UserRegistrationDto
import com.srsdev.tech.lms.models.enums.Role
import com.srsdev.tech.lms.models.payload.response.JwtResponse
import com.srsdev.tech.lms.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register-user")
    fun registerUser(@Valid @RequestBody body: UserRegistrationDto): User {
        return authService.register(body, setOf(Role.ROLE_USER))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping("/register-admin")
    fun registerAdmin(@Valid @RequestBody body: UserRegistrationDto): User {

        return authService.register(body, setOf(Role.ROLE_ADMIN))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody body: LoginDto): JwtResponse {
        return authService.login(body)
    }
}