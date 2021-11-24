package com.srsdev.tech.lms.controllers

import com.srsdev.tech.lms.exceptions.UserNotLoggedInException
import com.srsdev.tech.lms.models.*
import com.srsdev.tech.lms.security.services.UserDetailsImpl
import com.srsdev.tech.lms.services.AuthService
import com.srsdev.tech.lms.services.UserService
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import javax.servlet.http.HttpServletResponse

@CrossOrigin
@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val authService: AuthService
) {

    @GetMapping
    fun fetchUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication.name == "anonymousUser")
            throw UserNotLoggedInException("Log in first")
        val userDetails = authentication?.principal as UserDetailsImpl
        return authService.getUser(userDetails.id)
    }

    @GetMapping("/books")
    fun fetchBookList(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<Book> {
        return userService.fetchBooks(page, size)
    }

    @PostMapping("/books/{bookId}", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getBook(@PathVariable bookId: String,response: HttpServletResponse): ByteArray {
        response.addHeader("Content-Disposition", "attachment; filename=book")
        return userService.getBook("619358ec0994954fbde46e76", bookId)
//        return userService.getBook(this.fetchUser().id, bookId)
    }

//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/books/{bookId}/checkout")
    fun checkoutBook(@PathVariable bookId: String): CheckoutBook {
//        return userService.checkoutBook("619358ec0994954fbde46e76", bookId)
        return userService.checkoutBook(this.fetchUser().id, bookId)
    }

    @GetMapping("/books/subscribed")
    fun fetchSubscribedBookList(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<Book> {
//        return userService.fetchSubscribedBooks("619358ec0994954fbde46e76",page, size)
        return userService.fetchSubscribedBooks(this.fetchUser().id,page, size)
    }

    @GetMapping("/category")
    fun fetchCategory():List<Category>{
        return userService.fetchCategory()
    }

    @GetMapping("/author")
    fun fetchAuthor(): List<Author>{
        return userService.fetchAuthor()
    }
}