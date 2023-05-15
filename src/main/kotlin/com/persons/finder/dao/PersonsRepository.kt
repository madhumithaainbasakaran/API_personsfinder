package com.persons.finder.dao

import com.persons.finder.data.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonsRepository : JpaRepository<Person, Long>