package com.srsdev.tech.lms.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.joda.time.LocalDate
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Book(
    val id: String = ObjectId.get().toString(),
    val name: String,
    val publishDate: LocalDate = LocalDate.now(),
    val author: Author,
    val category: Category,
    val description: String? = "",
    @JsonIgnore
    val bookUrl: String,
    val imageUrl: String?
) {
    constructor(name: String, author: Author, category: Category, description: String?, bookUrl: String, imageUrl: String?) : this(
        ObjectId.get().toString(), name, LocalDate.now(), author, category, description, bookUrl, imageUrl
    )
}