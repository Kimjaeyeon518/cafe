package com.example.cafe.web.request

import jakarta.validation.constraints.NotNull

data class ChargeRequest(
    @field:NotNull
    var chargePoint: Long
)