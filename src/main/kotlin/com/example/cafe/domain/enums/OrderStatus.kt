package com.example.cafe.domain.enums

enum class OrderStatus(value: String) {
    CANCEL("주문취소"),
    REFUND("환불"),
    COMPLETE("결제완료"),
    WAIT("결제대기"),
}