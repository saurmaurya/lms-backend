package com.srsdev.tech.lms.models.dtos

import org.springframework.web.multipart.MultipartFile

data class BookDto(
    val name: String,
    val author: String,
    val category: String,
    val description: String?,
    val bookFile: MultipartFile,
    val imageUrl: String?
)