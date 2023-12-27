package com.example.cafe.domain

import jakarta.validation.constraints.NotNull

data class OrderLineDto(
    @field:NotNull
    var productId: Long,
    @field:NotNull
    var amount: Long,
)