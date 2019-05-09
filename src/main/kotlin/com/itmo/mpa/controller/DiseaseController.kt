package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse
import com.itmo.mpa.dto.response.MedicineResponse
import com.itmo.mpa.service.DiseaseService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Api("/diseases")
@RequestMapping("/diseases")
class DiseaseController(private val diseaseService: DiseaseService) {

    @GetMapping
    @ApiOperation("Get all diseases")
    fun getAllDiseases(): List<DiseaseResponse> = diseaseService.getAll()

    @GetMapping("/{id}/medicine")
    @ApiOperation("Get medicine for diseases by disiase id")
    fun getMedicineByDiseaseId(@Valid @PathVariable id: Long): List<MedicineResponse> = diseaseService.getMedicineByDiseaseId(id)

    @PostMapping
    @ApiOperation("Create new disease")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody diseaseRequest: DiseaseRequest) = diseaseService.createDisease(diseaseRequest)
}