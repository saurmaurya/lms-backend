package com.srsdev.tech.lms.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.srsdev.tech.lms.models.enums.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id val id: String = ObjectId.get().toString(),
    val email: String,
    @field:JsonIgnore
    val password: String,
    val roles: Set<Role>,
    val isActive: Boolean
) {
    constructor(email: String, password: String, role: Set<Role>, isActive: Boolean=false) : this(
        ObjectId.get().toString(), email, password, role, isActive
    )
}