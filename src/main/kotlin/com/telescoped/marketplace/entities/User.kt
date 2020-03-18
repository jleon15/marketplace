package com.telescoped.marketplace.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users", schema = "marketplace")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "username")
        var username: String,

        @Column(name = "first_name")
        var firstName: String,

        @Column(name = "last_name")
        var lastName: String,

        @JsonIgnore
        @Column(name = "password")
        var password: String,

        @Column
        var enabled: Boolean? = true,

        @JsonIgnore
        @OneToMany(mappedBy = "provider", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val shoppingCart: Set<Item> = emptySet(),

        @JsonIgnore
        @OneToMany(mappedBy = "buyer", cascade = [CascadeType.ALL], orphanRemoval = true)
        val providedItems: Set<Item> = emptySet()

) {
    constructor() : this(
            id = 0,
            username = "",
            firstName = "",
            lastName = "",
            password = ""
    )
}