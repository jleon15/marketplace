package com.telescoped.marketplace.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthenticationController {

    @GetMapping("/login")
    fun login(): String = "login-page"

    @GetMapping("/unauthorized")
    fun unauthorized(): String = "unauthorized-page"

}