package com.persons.finder.data

import javax.persistence.*

@Entity
@Table(name = "LOCATION")
data class Location(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val referenceId: Long, // use Person ID as reference ID
        val latitude: Double,
        val longitude: Double,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "person_id")
        val person: Person
) {
        constructor() : this(0, 0, 0.0, 0.0, Person())
        constructor(person: Person, referenceId: Long, latitude: Double, longitude: Double) : this(0, referenceId, latitude, longitude, person)
}