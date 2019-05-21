package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.service.AttributeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("/states")
@RequestMapping("states")
class StateController(
        private val attributeService: AttributeService
) {

    @GetMapping("{stateId}/attributes")
    @ApiOperation("Find attributes for state by id")
    fun findAttributes(
            @PathVariable stateId: Long
    ): List<DiseaseAttributeResponse> = attributeService.getForState(stateId)
}