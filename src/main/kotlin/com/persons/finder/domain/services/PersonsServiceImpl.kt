package com.persons.finder.domain.services

import com.persons.finder.dao.PersonsRepository
import com.persons.finder.data.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PersonsServiceImpl(@Autowired private val personsRepository: PersonsRepository) : PersonsService {

    override fun getById(id: Long): Person {
        return personsRepository.findById(id).orElse(null)
                ?: throw IllegalArgumentException("Person with ID $id not found")
    }

    override fun save(person: Person): Person {
        return personsRepository.save(person)
    }

    override fun getPersonsByIds(ids: List<Long>): List<Person> {
        return personsRepository.findAllById(ids)
    }

}