package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository: MongoRepository<Book, String> {
}