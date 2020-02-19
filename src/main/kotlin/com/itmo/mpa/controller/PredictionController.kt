package com.itmo.mpa.controller

import com.itmo.mpa.service.PredictionService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("/predictions")
@RequestMapping("predictions")
class PredictionController (
        private val predictionService: PredictionService
) {

    @PostMapping
    @ApiOperation("Make prediction on CNN model.")
    fun predict(
            @RequestBody predictionData: String
    ): ResponseEntity<String> {
        val tensorResponse = predictionService.makePrediction(predictionData)
        if( tensorResponse.isNotEmpty()) {
            return ResponseEntity.ok(tensorResponse)
        }
        return ResponseEntity.badRequest().body("TensorFlow could not parse data")
    }
}
