package com.example.cafe.api

import com.example.board.web.response.ListResponse
import com.example.board.web.response.SingleResponse
import com.example.cafe.domain.entity.Product
import com.example.cafe.domain.enums.Category
import com.example.cafe.service.ProductService
import com.example.cafe.web.request.BuyRequest
import com.example.cafe.web.request.ProductCreateRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductApi(val productService: ProductService) {

    @PostMapping("/products/buy")
    fun buy(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: BuyRequest
    ): ResponseEntity<SingleResponse<String>> {
        productService.buy(user.username.toLong(), request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("구매 완료 !"))
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    fun create(
        @Valid @RequestBody request: ProductCreateRequest
    ): ResponseEntity<SingleResponse<Product>> {
        val product = productService.create(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("상품 생성 완료 !", product))
    }

    @GetMapping("/products")
    fun find(
        @RequestParam category: Category
    ): ResponseEntity<ListResponse<Product>> {
        val products = productService.findByCategory(category)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ListResponse.successOf(products))
    }

    @GetMapping("/products/hit")
    fun findHit(): ResponseEntity<ListResponse<Product>> {
        val products = productService.findHit()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ListResponse.successOf(products))
    }
}