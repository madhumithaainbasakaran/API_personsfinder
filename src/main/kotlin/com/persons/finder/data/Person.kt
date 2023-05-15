package com.persons.finder.data

import javax.persistence.*

@Entity
@Table(name = "PERSON")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val name: String = "",
        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        val locations: MutableList<Location> = mutableListOf(),
        val locationReferenceId: Long? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
) {
        constructor() : this(0, "")
}