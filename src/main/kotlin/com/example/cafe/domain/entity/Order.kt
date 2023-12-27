package com.example.cafe.domain.entity

import com.example.cafe.domain.entity.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(totalPrice: Long): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderLines: MutableList<OrderLine> = ArrayList()

    @Column(name = "total_price")
    var totalPrice = totalPrice

    fun addOrderline(orderLine: OrderLine) {
        this.orderLines.add(orderLine)
    }
}