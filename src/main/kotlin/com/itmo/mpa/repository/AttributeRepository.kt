package com.itmo.mpa.repository

import com.itmo.mpa.entity.Attribute
import org.springframework.data.repository.CrudRepository

interface AttributeRepository : CrudRepository<Attribute, Long> {

    fun findByName(name: String): Attribute?
}