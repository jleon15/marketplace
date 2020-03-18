package com.telescoped.marketplace.models

import com.telescoped.marketplace.entities.Item

data class ItemType(
        val id: Int,
        val name: String,
        val cost: Double,
        val image: String,
        val description: String?,
        val provider: String?
)

fun Item.toItemType(): ItemType =
        ItemType(
                this.id,
                this.name,
                this.cost,
                this.image,
                this.description,
                "${this.provider.firstName} ${this.provider.lastName}"
        )