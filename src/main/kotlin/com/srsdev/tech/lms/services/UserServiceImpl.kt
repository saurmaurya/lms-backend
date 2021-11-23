package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.exceptions.InvalidRequestException
import com.srsdev.tech.lms.models.Author
import com.srsdev.tech.lms.models.Book
import com.srsdev.tech.lms.models.Category
import com.srsdev.tech.lms.models.CheckoutBook
import com.srsdev.tech.lms.repositories.AuthorRepository
import com.srsdev.tech.lms.repositories.BookRepository
import com.srsdev.tech.lms.repositories.CategoryRepository
import com.srsdev.tech.lms.repositories.CheckoutBookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserServiceImpl(
    private val bookRepository: BookRepository,
    private val checkoutBookRepository: CheckoutBookRepository,
    private val categoryRepository: CategoryRepository,
    private val authorRepository: AuthorRepository
) : UserService {
    override fun fetchBooks(page: Int, size: Int): Page<Book> {
        val paging = PageRequest.of(page, size).withSort(Sort.Direction.DESC, "publishDate")
        return bookRepository.findAll(paging)
    }

    override fun checkoutBook(userId: String, bookId: String): CheckoutBook {
        if (checkoutBookRepository.existsByUserIdAndBookIdAndIsExpiredFalse(userId, bookId))
            throw InvalidRequestException("Already checked out this book")
        return checkoutBookRepository
            .insert(CheckoutBook(userId, bookId, LocalDate.now().plusDays(15), false))
    }

    override fun fetchCategory(): List<Category> {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
    }

    override fun fetchAuthor(): List<Author> {
        return authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
    }

    override fun fetchSubscribedBooks(userId: String, page: Int, size: Int): Page<Book> {
        val checkoutBookList = checkoutBookRepository.findAllByUserIdAndIsExpiredFalse(userId)
        return if (checkoutBookList.isNotEmpty()) {
            val subscribedBooks = mutableListOf<Book>()
            checkoutBookList.forEach {
                subscribedBooks.add(
                    bookRepository.findById(it.bookId)
                        .orElseThrow { RuntimeException("Book with id ${it.bookId} not found") })
            }
            if (subscribedBooks.isNotEmpty()) {
                val paging = PageRequest.of(page, size).withSort(Sort.Direction.DESC, "publishDate")
                PageImpl(subscribedBooks, paging, subscribedBooks.size.toLong())
            } else
                Page.empty()
        } else
            Page.empty()
    }
}