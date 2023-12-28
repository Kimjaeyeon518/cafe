package com.example.cafe.web.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class BuyRequest(
    @field:NotEmpty
    var orderLines: List<OrderLineRequest>,
    @field:NotNull
    var totalPrice: Long,
)