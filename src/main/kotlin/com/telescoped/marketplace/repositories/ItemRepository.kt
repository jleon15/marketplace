package com.telescoped.marketplace.repositories

import com.telescoped.marketplace.entities.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository: JpaRepository<Item, Int> {

    @Query("select i from Item i where i.buyer = null and i.provider.id <> ?1")
    fun findAllForSale(userId: Int, pageable: Pageable): Page<Item>

    @Query("select i from Item i where i.buyer.id = ?1")
    fun viewShoppingCartItems(userId: Int, pageable: Pageable): Page<Item>

    @Query("select i from Item i where i.buyer = null and i.provider.id = ?1")
    fun findAllItemsForSaleByUser(userId: Int, pageable: Pageable): Page<Item>

}