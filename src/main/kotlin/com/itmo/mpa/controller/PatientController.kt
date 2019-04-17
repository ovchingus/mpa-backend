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

    @ApiOperation("Create patient")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @Valid @RequestBody patientRequest: PatientRequest
    ) = patientService.createPatient(patientRequest)

    @ApiOperation("Get all patients")
    @GetMapping
    fun getAll(): List<PatientResponse> = patientService.findAll()

    @ApiOperation("Find patient by id")
    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): PatientResponse = patientService.findPatient(id)
}