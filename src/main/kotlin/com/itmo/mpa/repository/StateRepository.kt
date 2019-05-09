package com.itmo.mpa.repository

import com.itmo.mpa.entity.State
import org.springframework.data.repository.CrudRepository

interface StateRepository : CrudRepository<State, Long>
