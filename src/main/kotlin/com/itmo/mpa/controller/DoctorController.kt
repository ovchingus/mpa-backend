package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.DoctorRequest
import com.itmo.mpa.dto.response.DoctorResponse
import com.itmo.mpa.service.DoctorService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api("/doctors")
@RequestMapping("doctors")
class DoctorController(private val doctorService: DoctorService) {

    @PostMapping
    @ApiOperation("Create new doctor")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody doctorRequest: DoctorRequest): Unit = doctorService.createDoctor(doctorRequest)

    @GetMapping
    @ApiOperation("Get all doctors")
    fun getAllDoctors(): List<DoctorResponse> = doctorService.findAll()

    @GetMapping("{id}")
    @ApiOperation("Find doctor by id")
    fun findDoctorById(@PathVariable id: Long): DoctorResponse = doctorService.findById(id)

    @GetMapping("find")
    @ApiOperation("Find doctors with name matching given")
    fun findDoctorByName(@RequestParam name: String): List<DoctorResponse> = doctorService.findByName(name)
}
