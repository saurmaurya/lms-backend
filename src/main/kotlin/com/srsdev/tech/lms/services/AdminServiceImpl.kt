package com.srsdev.tech.lms.services

import com.amazonaws.services.s3.model.ObjectMetadata
import com.srsdev.tech.lms.models.Author
import com.srsdev.tech.lms.models.Book
import com.srsdev.tech.lms.models.Category
import com.srsdev.tech.lms.models.dtos.BookDto
import com.srsdev.tech.lms.repositories.AuthorRepository
import com.srsdev.tech.lms.repositories.BookRepository
import com.srsdev.tech.lms.repositories.CategoryRepository
import com.srsdev.tech.lms.utils.AwsS3Service
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.IOException
import java.util.*

@Service
class AdminServiceImpl(
    private val awsS3Service: AwsS3Service,
    @Value("\${cloud.aws.bucketName}")
    private val bucketName: String,
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val categoryRepository: CategoryRepository
) : AdminService {
    override fun publishBook(bookDto: BookDto): Book {
        val (name, author, category, description, bookFile, imageUrl) = bookDto
        if (bookFile.isEmpty)
            throw IllegalStateException("Cannot upload empty file")
        // PDF validation pending
        val metadata = ObjectMetadata()
        metadata.contentLength = bookFile.size
        metadata.contentType = bookFile.contentType
        bookFile.originalFilename?.let {
            metadata.addUserMetadata("filename", it.substringBeforeLast("."))
        }
        val bucketName = String.format("%s/%s", bucketName, LocalDate.now().toString())
        val key = "book".plus(UUID.randomUUID().toString())
        try {
            awsS3Service.uploadFile(bucketName, key, metadata, bookFile.inputStream)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to upload file", e)
        }
        val authorObj = authorRepository.findAuthorByName(author)
            ?: authorRepository.insert(Author(name = author))
        val categoryObj =
            categoryRepository.findCategoryByName(category)
                ?: categoryRepository.insert(Category(name = category))
        val book = Book(name,authorObj,categoryObj,description,key,imageUrl)
        return bookRepository.insert(book)
    }

    override fun downloadBook(): ByteArray {
        return awsS3Service.downloadFile(
            String.format("%s/%s", bucketName, LocalDate.now().toString()),
            "book18bcec79-3a80-409e-88ae-220bc23591e5"
        )
    }
}