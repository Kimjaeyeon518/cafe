package com.example.cafe.web.request

import com.example.cafe.domain.enums.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductCreateRequest(
    @field:NotBlank
    val name: String,

    @field:NotNull
    val category: Category,

    var description: String,

    @field:NotNull
    val price: Long,

    @field:NotNull
    val amount: Long
)