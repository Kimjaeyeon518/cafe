package com.example.cafe.util

import com.example.cafe.domain.entity.Order
import com.example.cafe.domain.entity.Product
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import java.util.stream.Collectors

class EasyRandomPlugin {

    companion object {
        private val parameters = EasyRandomParameters()
            .collectionSizeRange(4, 4)

        private val generator: EasyRandom = EasyRandom(parameters)

        fun getRandomProducts(): List<Product> {
            return generator.objects(Product::class.java, 4)
                .collect(Collectors.toList())
        }

        fun getRandomOrder(): Order {
            val order = generator.nextObject(Order::class.java)
            order.orderLines = order.orderLines
                .distinctBy { it.product.productId }
                .sortedBy { it.amount }
                .toMutableList()
            return order
        }

        fun getRandomSortedNums(): List<Long> {
            return generator.objects(Long::class.java, 4)
                .collect(Collectors.toList())
                .sorted()
        }
    }
}