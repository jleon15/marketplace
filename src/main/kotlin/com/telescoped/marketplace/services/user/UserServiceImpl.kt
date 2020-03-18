package com.telescoped.marketplace.services.user

import com.telescoped.marketplace.entities.User
import com.telescoped.marketplace.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {

    override fun findById(id: Int): Optional<User> =
        userRepository.findById(id)

    override fun findByUsername(username: String): Optional<User> =
        userRepository.findByUsername(username)

}