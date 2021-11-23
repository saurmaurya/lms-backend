package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.models.Author
import com.srsdev.tech.lms.models.Book
import com.srsdev.tech.lms.models.Category
import com.srsdev.tech.lms.models.CheckoutBook
import org.springframework.data.domain.Page

interface UserService {
    fun fetchBooks(page: Int, size: Int): Page<Book>
    fun checkoutBook(userId: String, bookId: String): CheckoutBook
    fun fetchCategory(): List<Category>
    fun fetchAuthor(): List<Author>
    fun fetchSubscribedBooks(userId: String, page: Int, size: Int): Page<Book>
}
