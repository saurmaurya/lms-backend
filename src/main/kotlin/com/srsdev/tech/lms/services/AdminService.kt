package com.srsdev.tech.lms.services

import com.srsdev.tech.lms.models.Book
import com.srsdev.tech.lms.models.dtos.BookDto

interface AdminService {
    fun publishBook(bookDto: BookDto): Book
    fun downloadBook(): ByteArray
}