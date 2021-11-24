package com.srsdev.tech.lms.repositories

import com.srsdev.tech.lms.models.CheckoutBook
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface CheckoutBookRepository : MongoRepository<CheckoutBook, String> {
    fun existsByUserIdAndBookIdAndIsExpiredFalse(userId: String, bookId: String): Boolean
    fun findByUserIdAndBookIdAndIsExpiredFalse(userId: String, bookId: String): CheckoutBook?
    fun existsByUserIdAndBookIdAndValidTillGreaterThanEqual(userId: String,bookId: String,validTill: LocalDate): Boolean
    fun findAllByIsExpiredFalseAndValidTillBefore(date: LocalDate): List<CheckoutBook>
    fun findAllByUserIdAndIsExpiredFalse(userId: String): List<CheckoutBook>
    fun findAllByUserIdAndIsExpiredFalse(userId: String, pageable: Pageable): Page<CheckoutBook>
}