package com.jyk.qrorder.domain.entity

import com.jyk.qrorder.domain.enums.OrderStatus
import jakarta.persistence.*

@Entity
@Table(name = "order")
class Order(shopId: Long, tableId: Long, status: OrderStatus, totalPrice: Long) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderLines: MutableList<OrderLine> = ArrayList()

    @Column(name = "shop_id")
    var shopId = shopId

    @Column(name = "table_id")
    var tableId = tableId

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status = status

    @Column(name = "total_price")
    var totalPrice = totalPrice
}