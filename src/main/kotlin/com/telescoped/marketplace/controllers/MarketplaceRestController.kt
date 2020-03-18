package com.telescoped.marketplace.controllers

import com.telescoped.marketplace.Constants
import com.telescoped.marketplace.ErrorMessages
import com.telescoped.marketplace.entities.Item
import com.telescoped.marketplace.entities.User
import com.telescoped.marketplace.models.toItemType
import com.telescoped.marketplace.services.item.ItemService
import com.telescoped.marketplace.services.user.UserService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.lang.IllegalStateException
import java.lang.NumberFormatException
import java.util.regex.Pattern

@Controller
class MarketplaceRestController(private val itemService: ItemService,
                                private val userService: UserService) {

    @GetMapping("/sell")
    fun addItem(): String = "sell-item"

    @GetMapping("/viewItemsForSale/{pageNumber}")
    fun getItemsForSale(model: Model, @PathVariable pageNumber: Int): String {
        val itemsPage = itemService.getAllItemsForSale(getCurrentUser().id,
                PageRequest.of(pageNumber, Constants.MAX_PAGE_SIZE))

        model.addAllAttributes(itemsPage.buildAttributesMap())

        return "items-for-sale"
    }

    @GetMapping("/viewMyItemsForSale/{pageNumber}")
    fun viewUserItemsForSale(model: Model, @PathVariable pageNumber: Int): String {
        val itemsPage = itemService.findAllItemsForSaleByUser(getCurrentUser().id,
                PageRequest.of(pageNumber, Constants.MAX_PAGE_SIZE))

        model.addAllAttributes(itemsPage.buildAttributesMap())

        return "user-items-for-sale"
    }

    @GetMapping("/viewShoppingCartItems/{pageNumber}")
    fun getShoppingCartItems(model: Model, @PathVariable pageNumber: Int): String {
        val itemsPage = itemService.viewShoppingCartItems(getCurrentUser().id,
                PageRequest.of(pageNumber, Constants.MAX_PAGE_SIZE))

        model.addAllAttributes(itemsPage.buildAttributesMap())

        return "view-shopping-cart"
    }

    @PostMapping("/api/marketplace/unlistItem/{itemId}")
    fun unlistItemForSale(@PathVariable itemId: Int): String {
        val item = itemService.findItem(itemId)
                .orElseThrow { NoSuchElementException(ErrorMessages.itemNotFound(itemId)) }

        if (item.provider.id != getCurrentUser().id) throw IllegalStateException(ErrorMessages.UNAUTHORIZED_ACTION)

        itemService.unlistItemForSale(itemId)

        return "redirect:/viewMyItemsForSale/0"
    }

    @PostMapping("/api/marketplace/sellItem")
    fun listItemForSale(@RequestParam itemValues: Map<String, String>): String {
        if (itemValues.areItemsInputsValid().not())
            return "redirect:/sell?error"

        val item = with(itemValues) {
            Item(0, this["name"]!!.trim(),
                    this["cost"]!!.trim().toDouble(),
                    this["image"]!!.trim(),
                    this["description"].nullIfEmpty(),
                    getCurrentUser())
        }

        itemService.listItemForSale(item)

        return "redirect:/viewItemsForSale/0"
    }


    @PostMapping("/api/marketplace/addItemToCart/{itemId}")
    fun addItemToCart(@PathVariable itemId: Int): String {
        val item = itemService.findItem(itemId)
                .orElseThrow { NoSuchElementException(ErrorMessages.itemNotFound(itemId)) }

        if (item.provider.id == getCurrentUser().id) throw IllegalStateException(ErrorMessages.UNAUTHORIZED_ACTION)

        item.buyer = getCurrentUser()

        itemService.addItemToShoppingCart(item)

        return "redirect:/viewShoppingCartItems/0"
    }

    @PostMapping("/api/marketplace/removeItemFromCart/{itemId}")
    fun removeItemFromCart(@PathVariable itemId: Int): String {
        val item = itemService.findItem(itemId)
                .orElseThrow { NoSuchElementException(ErrorMessages.itemNotFound(itemId)) }

        if (item.buyer?.id != getCurrentUser().id) throw IllegalStateException(ErrorMessages.UNAUTHORIZED_ACTION)

        item.buyer = null

        itemService.removeItemFromShoppingCart(item)

        return "redirect:/viewShoppingCartItems/0"
    }

    private fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name

        return userService.findByUsername(username)
                .orElseThrow { IllegalStateException(ErrorMessages.userNotFound(username)) }
    }

    private fun Page<Item>.buildAttributesMap(): Map<String, Any> =
            mapOf(
                    "items" to this.content.map { it.toItemType() },
                    "totalPages" to this.totalPages,
                    "totalItems" to this.totalElements,
                    "isFirst" to this.isFirst,
                    "isLast" to this.isLast,
                    "currentPage" to this.number
            )

    private fun Map<String, String>.areItemsInputsValid(): Boolean =
            try {
                this.size <= 5
                        && this.keys.containsAll(setOf("name", "cost", "image"))
                        && this["image"]!!.isNotBlank()
                        && this["name"]!!.isNotBlank()
                        && Pattern.matches(Constants.ALPHANUMERIC_VALID_REGEX, this["name"]!!)
                        && this["description"].nullIfEmpty()
                        ?.let { Pattern.matches(Constants.ALPHANUMERIC_VALID_REGEX, it) } ?: true
                        && try {
                    this["cost"]!!.toDouble()
                    true
                } catch (e: NumberFormatException) {
                    false
                }
            } catch (e: Exception) {
                false
            }

    private fun String?.nullIfEmpty(): String? = this?.let { if (this.isBlank()) null else this }
}