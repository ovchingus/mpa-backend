package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.StateMachineService
import io.swagger.annotations.ApiOperation
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class StateController(
    private val attributeService: AttributeService,
    private val stateMachineService: StateMachineService,
    private val resourceLoader: ResourceLoader
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

    @GetMapping("states/{stateId}/images", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ApiOperation("Get image for state by id")
    @ResponseBody
    fun findImage(
        @PathVariable stateId: Long
    ): Resource {
        return resourceLoader.getResource("classpath:${stateMachineService.getImageState(stateId).canonicalPath}")
    }
}

