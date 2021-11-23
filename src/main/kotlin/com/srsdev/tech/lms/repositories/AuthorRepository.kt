package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.Author
import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorRepository: MongoRepository<Author, String> {
    fun findAuthorByName(name: String): Author?
}