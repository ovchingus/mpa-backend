package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.PatientRequest
import com.itmo.mpa.dto.response.PatientResponse
import com.itmo.mpa.service.PatientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api("/patients")
@RequestMapping("patients")
class PatientController(private val patientService: PatientService) {

    @PostMapping
    @ApiOperation("Create patient")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody patientRequest: PatientRequest
    ): PatientResponse = patientService.createPatient(patientRequest)

    @GetMapping
    @ApiOperation("Get all patients")
    fun getAll(): List<PatientResponse> = patientService.findAll()

    @GetMapping("{id}")
    @ApiOperation("Find patient by id")
    fun getById(@PathVariable id: Long): PatientResponse = patientService.findPatient(id)
}
