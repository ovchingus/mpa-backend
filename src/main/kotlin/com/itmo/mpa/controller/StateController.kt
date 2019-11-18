package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.DiseaseAttributeResponse
import com.itmo.mpa.dto.response.StateMachineResponse
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.StateMachineService
import io.swagger.annotations.ApiOperation
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping("states/{stateId}/images", produces = [MediaType.IMAGE_JPEG_VALUE])
    @ApiOperation("Get image for state by id")
    @ResponseBody
    fun findImage(
            @PathVariable stateId: Long
    ): ResponseEntity<ClassPathResource> {
        println(stateMachineService.getImageState(stateId).canonicalPath)
        return ResponseEntity
                .ok()
                .body(ClassPathResource(stateMachineService.getImageState(stateId).canonicalPath))
    }
}

