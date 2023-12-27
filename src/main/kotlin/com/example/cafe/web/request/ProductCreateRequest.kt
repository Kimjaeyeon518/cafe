package com.example.cafe.web.request

import jakarta.validation.constraints.NotNull

data class ProductBuyRequest(
    @field:NotNull
    var productId: Long,
    @field:NotNull
    var amount: Long,
)