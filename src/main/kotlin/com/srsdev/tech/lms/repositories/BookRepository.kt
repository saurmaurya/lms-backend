package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository: MongoRepository<Book, String> {
    fun findAllById(id: Iterable<String>, pageable: Pageable): List<Book>
}