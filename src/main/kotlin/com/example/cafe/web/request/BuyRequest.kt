package com.example.cafe.web.request

import com.example.cafe.domain.entity.OrderLine
import jakarta.validation.constraints.NotNull

data class ProductBuyRequest(
    var orderLines: List<OrderLine>
    @field:NotNull
    var productId: Long,
    @field:NotNull
    var amount: Long,
)