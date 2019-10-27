package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.StateMachineService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class StateController(
    private val attributeService: AttributeService,
    private val stateMachineService: StateMachineService
) {

    @GetMapping("diseases/{diseaseId}/states")
    @ApiOperation("Get state machine")
    fun getStateMachineForDisease(
        @PathVariable diseaseId: Long
    ): StateMachineResponse = stateMachineService.getStateMachineForDisease(diseaseId)

    @GetMapping("states/{stateId}/attributes")
    @ApiOperation("Find attributes for state by id")
    fun findAttributes(
        @PathVariable stateId: Long
    ): List<DiseaseAttributeResponse> = attributeService.getForState(stateId)
}
