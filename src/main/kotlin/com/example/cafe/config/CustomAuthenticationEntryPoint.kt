package com.example.cafe.config

import com.example.cafe.exception.NotFoundException
import com.example.cafe.exception.ServerException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import java.util.concurrent.TimeoutException

@Component
class CustomAuthenticationEntryPoint(
    @Qualifier("handlerExceptionResolver")  // HandlerExceptionResolver의 빈이 두 종류가 있기 때문에 @Qualifier
    private val resolver: HandlerExceptionResolver
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val exception = when (response!!.status) {
            200 -> return
            401, 403 -> request!!.getAttribute("exception") as Exception
            404 -> NotFoundException("요청하신 리소스를 찾을 수 없습니다.")
            408 -> TimeoutException("요청 시간이 초과되었습니다.")
            else -> ServerException("서버에 문제가 발생했습니다.")
        }
        resolver.resolveException(request!!, response, null, exception)
    }
}