package com.itmo.mpa.repository

import com.itmo.mpa.model.Patient
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.HashMap

@Repository
class PatientStubRepository : PatientRepository {

    private val storage = HashMap<Int, Patient>()

    override fun findAll(): Iterable<Patient> = storage.values

    override fun findById(id: Int): Optional<Patient> = Optional.ofNullable(storage[id])

    override fun <S : Patient> save(patient: S): S {
        val id = patient.id
        if (id == null) {
            val newId = storage.size + 1
            storage[newId] = patient
            patient.id = newId
        } else {
            storage[id] = patient
        }
        return patient
    }
}