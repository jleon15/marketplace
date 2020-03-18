package com.telescoped.marketplace.services.item

import com.telescoped.marketplace.entities.Item
import com.telescoped.marketplace.repositories.ItemRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ItemServiceImpl(private val itemRepository: ItemRepository) : ItemService {

    override fun findItem(id: Int): Optional<Item> =
            itemRepository.findById(id)


    override fun listItemForSale(item: Item) {
        itemRepository.save(item)
    }

    override fun unlistItemForSale(id: Int) {
        itemRepository.deleteById(id)
    }

    override fun addItemToShoppingCart(item: Item) {
        itemRepository.save(item)
    }

    override fun getAllItemsForSale(userId: Int, pageable: Pageable): Page<Item> =
            itemRepository.findAllForSale(userId, pageable)

    override fun removeItemFromShoppingCart(item: Item) {
        itemRepository.save(item)
    }

    override fun viewShoppingCartItems(userId: Int, pageable: Pageable): Page<Item> =
            itemRepository.viewShoppingCartItems(userId, pageable)

    override fun findAllItemsForSaleByUser(userId: Int, pageable: Pageable): Page<Item> =
        itemRepository.findAllItemsForSaleByUser(userId, pageable)

}