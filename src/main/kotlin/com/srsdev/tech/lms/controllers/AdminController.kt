package com.srsdev.tech.lms.controllers

import com.srsdev.tech.lms.models.CheckoutBook
import com.srsdev.tech.lms.models.User
import com.srsdev.tech.lms.models.dtos.BookDto
import com.srsdev.tech.lms.services.AdminService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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

    @GetMapping("/users")
    fun fetchUserList(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<User> {
        return adminService.fetchUserList(page, size)
    }

    @GetMapping("/users/{userId}/subscribed-books")
    fun getSubscribedBookList(
        @PathVariable userId: String, @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<CheckoutBook> {
        return adminService.getSubscribedBookList(userId, page, size)
    }

    @PostMapping(
        "/revoke-access",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun revokeAccess(@RequestParam("file") file: MultipartFile) {
        adminService.revokeAccess(file)
    }

    @GetMapping(path = ["/download-book"], produces = [MediaType.APPLICATION_PDF_VALUE])
    fun downloadBook(response: HttpServletResponse): ByteArray {
        response.addHeader("Content-Disposition", "attachment; filename=book")
        return adminService.downloadBook()
    }
}