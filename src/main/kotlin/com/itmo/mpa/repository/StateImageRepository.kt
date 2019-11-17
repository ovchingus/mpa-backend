package com.itmo.mpa.repository

import com.itmo.mpa.entity.StateImage
import org.springframework.data.repository.CrudRepository

interface StateImageRepository : CrudRepository<StateImage, Long>{
    fun findByMachineState(state: String): StateImage
}