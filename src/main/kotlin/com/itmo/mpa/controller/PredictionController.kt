package com.itmo.mpa.controller

import com.itmo.mpa.service.PredictionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@Api("/predictions")
@RequestMapping("predictions")
class PredictionController (
        private val predictionService: PredictionService
) {

    @PostMapping
    @ApiOperation("Make prediction on CNN model.")
    fun predict(
            @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<String> {
        val predictionData = multipartFile.bytes.toString(Charsets.UTF_8)
        val tensorResponse = predictionService.makePrediction(predictionData)
        if( !tensorResponse.startsWith("Error")) {
            return ResponseEntity.ok(tensorResponse)
        }
        return ResponseEntity.badRequest().body(tensorResponse)
    }
}
