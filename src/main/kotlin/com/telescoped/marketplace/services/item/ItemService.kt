package com.telescoped.marketplace.services.item

import com.telescoped.marketplace.entities.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface ItemService {

    fun findItem(id: Int): Optional<Item>

    fun listItemForSale(item: Item)

    fun unlistItemForSale(id: Int)

    fun removeItemFromShoppingCart(item: Item)

    fun getAllItemsForSale(userId: Int, pageable: Pageable): Page<Item>

    fun addItemToShoppingCart(item: Item)

    fun viewShoppingCartItems(userId: Int, pageable: Pageable): Page<Item>

    fun findAllItemsForSaleByUser(userId: Int, pageable: Pageable): Page<Item>

}