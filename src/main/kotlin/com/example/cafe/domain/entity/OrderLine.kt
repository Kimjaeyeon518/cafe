package com.example.cafe.domain.entity

import com.example.cafe.domain.entity.Order
import jakarta.persistence.*

@Entity
@Table(name = "order_line")
class OrderLine(order: Order, product: Product, amount: Long) {

    init {
        order.addOrderline(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderLineId: Long? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order = order

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id", nullable = false)
    var product = product

    @Column(name = "amount")
    var amount = amount
}