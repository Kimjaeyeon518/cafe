package com.example.cafe.domain.enums

enum class OrderStatus(value: String) {
    WAITING("결제대기"),
    COMPLETE("주문완료"),
    CONFIRMED("주문확정"),
    CANCEL("주문취소"),
}