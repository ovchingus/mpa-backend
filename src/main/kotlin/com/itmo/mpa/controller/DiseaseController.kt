package com.itmo.mpa.controller

import com.itmo.mpa.dto.request.DiseaseRequest
import com.itmo.mpa.dto.response.DiseaseResponse
import com.itmo.mpa.service.DiseaseService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api("/diseases")
@RequestMapping("/diseases")
class DiseaseController(private val diseaseService: DiseaseService) {

    @GetMapping("/getAll")
    @ApiOperation("Get all diseases")
    fun getAllDiseases() : List<DiseaseResponse> = diseaseService.getAll()


    @PostMapping("/create")
    @ApiOperation("Create new disease")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody diseaseRequest: DiseaseRequest) = diseaseService.createDisease(diseaseRequest)
}