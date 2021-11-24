package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.models.Book
import com.srsdev.tech.lms.models.CheckoutBook
import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.BookDto
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface AdminService {
    fun publishBook(bookDto: BookDto): Book
    fun downloadBook(): ByteArray
    fun fetchUserList(page: Int, size: Int): Page<User>
    fun getSubscribedBookList(userId: String, page: Int, size: Int):Page<CheckoutBook>
    fun revokeAccess(file: MultipartFile)
}