package com.itmo.mpa.controller

import com.itmo.mpa.controller.request.PatientRequest
import com.itmo.mpa.controller.request.StatusRequest
import com.itmo.mpa.controller.responce.PatientResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController("patients")
class PatientController {

    @PostMapping
    fun create(@Valid @RequestBody patientRequest: PatientRequest) {
        throw NotImplementedError()
    }

    @GetMapping
    fun getAll(): List<PatientResponse> {
        throw NotImplementedError()
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: String): ResponseEntity<PatientResponse> {
        throw NotImplementedError()
    }

    @PatchMapping("{id}/status")
    fun changePatientStatus(@PathVariable id: String, @Valid @RequestBody statusRequest: StatusRequest) {
        throw NotImplementedError()
    }
}