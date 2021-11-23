package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.Category
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepository: MongoRepository<Category, String> {
    fun findCategoryByName(name: String): Category?
}