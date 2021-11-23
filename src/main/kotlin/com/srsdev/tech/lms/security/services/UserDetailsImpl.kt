package com.srsdev.tech.lms.security.services

import com.fasterxml.jackson.annotation.JsonIgnore
import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.enums.Role
import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsImpl(
    val id: String = ObjectId.get().toString(),
    val email: String,
    @field:JsonIgnore
    private val password: String,
    val roles: Set<Role>,
    private val isActive: Boolean
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = ArrayList<SimpleGrantedAuthority>()
        roles.forEach { role: Role -> authorities.add(SimpleGrantedAuthority(role.name)) }
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return isActive
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return id == user.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (email.hashCode())
        result = 31 * result + password.hashCode()
        result = 31 * result + authorities.hashCode()
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): UserDetailsImpl {
            return UserDetailsImpl(
                user.id,
                user.email,
                user.password,
                user.roles,
                user.isActive,
            )
        }
    }
}