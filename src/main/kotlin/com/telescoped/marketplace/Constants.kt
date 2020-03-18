package com.telescoped.marketplace

object ErrorMessages {
    const val UNAUTHORIZED_ACTION = "The user is not authorized to perform such action"

    private const val ITEM_NOT_FOUND_TEMPLATE = "Item not found with the id: %s"
    private const val USER_NOT_FOUND_TEMPLATE = "User not found with the id: %s"

    fun itemNotFound(itemId: Int): String = ITEM_NOT_FOUND_TEMPLATE.format(itemId)

    fun itemNotFound(itemId: String): String = ITEM_NOT_FOUND_TEMPLATE.format(itemId)

    fun userNotFound(userId: String): String = USER_NOT_FOUND_TEMPLATE.format(userId)
}

object Constants {
    const val MAX_PAGE_SIZE = 12
    const val ALPHANUMERIC_VALID_REGEX = "[A-Za-z0-9\\s]+"
}