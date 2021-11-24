package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.LoginDto
import com.srsdev.tech.lms.models.dtos.UserRegistrationDto
import com.srsdev.tech.lms.models.enums.Role
import com.srsdev.tech.lms.models.payload.response.JwtResponse
import com.srsdev.tech.lms.repositories.UserRepository
import com.srsdev.tech.lms.security.jwt.JwtUtils
import com.srsdev.tech.lms.security.services.UserDetailsImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) : AuthService {

    override fun register(userBody: UserRegistrationDto, roles: Set<Role>): User {
        if (userRepository.existsUserByEmail(userBody.email!!)) {
            throw IllegalArgumentException("Error: Email already exists")
        }
//        val userRoles = mutableSetOf<Role>()
//        for (userRole in userBody.roles!!) {
//            val item = Role.values().filter { role: Role -> role.name.equals(userRole, ignoreCase = true) }.toSet()
//            userRoles.addAll(item)
//        }
        val user = User(userBody.email, encoder.encode(userBody.password), roles, true)
        return userRepository.insert(user)
    }

    override fun login(loginDto: LoginDto): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = authentication?.let { jwtUtils.generateJwtToken(it) }
        val userDetails = authentication?.principal as UserDetailsImpl
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return JwtResponse(
            jwt!!, userDetails.id, userDetails.email, roles
        )
    }

    override fun getUser(userId: String): User {
        return userRepository.findById(userId).orElseThrow { UsernameNotFoundException("user not found") }
    }
}