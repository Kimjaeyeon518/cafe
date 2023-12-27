package com.example.board.web.response

import com.example.cafe.web.response.ApiResponseCode
import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class ErrorResponse(
    var code: ApiResponseCode? = null,
    var message: String? = null
)