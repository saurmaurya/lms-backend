package com.srsdev.tech.lms.security.services

import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    var userRepository: UserRepository
): UserDetailsService {
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetailsImpl {
        val user: User = userRepository.findUserByEmail(email)
            ?: throw UsernameNotFoundException(
                "User Not Found with email: $email"
            )
        return user.let { UserDetailsImpl.build(it) }
    }
}