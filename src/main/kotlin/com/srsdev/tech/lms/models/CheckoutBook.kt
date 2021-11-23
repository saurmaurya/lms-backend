package com.srsdev.tech.lms.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDate

data class CheckoutBook(
    @Id val id: String = ObjectId.get().toString(),
    val userId: String,
    val bookId: String,
    val validTill: LocalDate,
    var isExpired: Boolean
) {
    constructor(userId: String, bookId: String, validTill: LocalDate, isExpired: Boolean) : this(
        ObjectId.get().toString(), userId, bookId, validTill, isExpired
    )
}