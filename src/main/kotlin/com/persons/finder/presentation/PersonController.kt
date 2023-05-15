package com.persons.finder.presentation

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.domain.services.LocationsService
import com.persons.finder.domain.services.PersonsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/persons")
class PersonController @Autowired constructor(
        private val personsService: PersonsService,
        private val locationsService: LocationsService) {

    /*
        TODO PUT API to update/create someone's location using latitude and longitude
        (JSON) Body
     */
    @PutMapping("/{id}/location")
    fun addOrUpdateLocation(@PathVariable id: Long, @RequestBody location: Location): ResponseEntity<Unit> {
        val person = personsService.getById(id)
        return if (person != null) {
            val updatedPerson = person.copy(
                    locationReferenceId = location.referenceId,
                    latitude = location.latitude,
                    longitude = location.longitude
            )
            personsService.save(updatedPerson)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /*
        TODO POST API to create a 'person'
        (JSON) Body and return the id of the created entity
    */
    @PostMapping("")
    fun createPerson(@RequestBody person: Person): ResponseEntity<Long> {
        val savedPerson = personsService.save(person)
        return ResponseEntity(savedPerson.id, HttpStatus.CREATED)
    }

    /*
        TODO GET API to retrieve people around query location with a radius in KM, Use query param for radius.
        TODO API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
        // API would be called using John's id and a radius 10km
     */
    @GetMapping("/{id}/around")
    fun getPeopleAround(@PathVariable id: Long, @RequestParam radiusInKm: Double): List<Long> {
        val person = personsService.getById(id)
        return if (person != null && person.latitude != null && person.longitude != null) {
            val locations = locationsService.findAround(person.latitude, person.longitude, radiusInKm)
            locations.map { it.referenceId }
        } else {
            emptyList()
        }
    }

    /*
        TODO GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     */
    @GetMapping("/{id}")
    fun getPersonById(@PathVariable id: Long): Person {
        return personsService.getById(id)
    }

    @GetMapping("/names")
    fun getPersonNames(@RequestParam("ids") ids: List<Long>): List<String> {
        val persons = personsService.getPersonsByIds(ids)
        return persons.map { it.name }
    }

    /*@GetMapping(" ")
    fun getExample(): String {
        return "Hello Example"
    }*/

}