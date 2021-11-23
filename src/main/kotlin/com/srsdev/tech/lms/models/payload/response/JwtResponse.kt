package com.srsdev.tech.lms.models.payload.response

data class JwtResponse(
    var accessToken: String,
    var id: String,
    var email: String,
    val roles: List<String>,
    var tokenType: String = "Bearer"
)