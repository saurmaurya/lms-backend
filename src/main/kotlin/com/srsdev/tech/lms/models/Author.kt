package com.srsdev.tech.lms.models

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Author(
    val id: String=ObjectId.get().toString(),
    val name: String
)
