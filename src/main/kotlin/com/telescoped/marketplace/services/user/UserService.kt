package com.telescoped.marketplace.services.user

import com.telescoped.marketplace.entities.User
import java.util.*

interface UserService {

    fun findById(id: Int): Optional<User>

    fun findByUsername(username: String): Optional<User>
}