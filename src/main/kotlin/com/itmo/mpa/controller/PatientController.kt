package com.itmo.mpa.controller

import com.itmo.mpa.controller.request.PatientRequest
import com.itmo.mpa.controller.request.StatusRequest
import com.itmo.mpa.controller.response.PatientResponse
import com.itmo.mpa.service.PatientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("patients")
class PatientController(private val patientService: PatientService) {

    @PostMapping
    fun create(@Valid @RequestBody patientRequest: PatientRequest) = patientService.createPatient(patientRequest)

    @GetMapping
    fun getAll(): List<PatientResponse> = patientService.findAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<PatientResponse> {
        val patient = patientService.findPatient(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(patient)
    }

    @PatchMapping("{id}/status")
    fun changePatientStatus(
            @PathVariable id: Int,
            @Valid @RequestBody statusRequest: StatusRequest
    ) = patientService.changeStatus(id, statusRequest)
}