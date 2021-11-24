package com.srsdev.tech.lms.models.dtos

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRegistrationDto(
    @field:Email(message = "invalid email address")
    @field:NotBlank(message = "email cannot be empty")
    @field:NotNull(message = "email cannot be null")
    val email: String?,
    @field:NotBlank(message = "password cannot be empty")
    @field:NotNull(message = "password cannot be null")
    val password: String?,
//    @field:Enum(enumClass = Role::class, ignoreCase = true)
//    @field:NotNull(message = "role cannot be null")
//    val roles: Set<String>?
)
