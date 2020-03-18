package com.telescoped.marketplace.entities

import javax.persistence.*

@Entity
@Table(name = "items", schema = "marketplace")
data class Item(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,

        @Column
        var name: String,

        @Column
        var cost: Double,

        @Column
        var image: String,

        @Column
        var description: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        var provider: User,

        @ManyToOne(fetch = FetchType.LAZY)
        var buyer: User? = null
) {
    constructor() : this(
            id = 0,
            name = "",
            cost = 0.0,
            image = "",
            provider = User()

    )
}