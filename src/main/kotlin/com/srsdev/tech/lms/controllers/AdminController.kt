package com.srsdev.tech.lms.controllers

import com.srsdev.tech.lms.models.dtos.BookDto
import com.srsdev.tech.lms.services.AdminService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import javax.servlet.http.HttpServletResponse

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService
) {
    @PostMapping(
        path = ["/publish-book"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun publishBook(
        @ModelAttribute bookDto: BookDto
    ) {
        adminService.publishBook(bookDto)
    }

    @GetMapping(path = ["/download-book"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun downloadBook(response: HttpServletResponse): ByteArray {
        response.addHeader("Content-Disposition", "attachment; filename=book")
        return adminService.downloadBook()
    }
}