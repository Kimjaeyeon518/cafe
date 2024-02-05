package com.example.cafe.domain.entity

import jakarta.persistence.*

@Entity
class Favorite(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val favoriteId: Long? = null

}