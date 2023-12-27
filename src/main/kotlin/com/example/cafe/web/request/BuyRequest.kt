package com.example.cafe.web.request

import jakarta.validation.constraints.NotEmpty

data class BuyRequest(
    @field:NotEmpty
    var orderLines: List<OrderLineRequest>
)