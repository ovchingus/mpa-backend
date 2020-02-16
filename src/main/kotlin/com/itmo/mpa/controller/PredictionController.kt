package com.itmo.mpa.controller

import com.itmo.mpa.dto.response.PredictionResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api("/predictions")
@RequestMapping("predictions")
class PredictionController {

    @PostMapping
    @ApiOperation("Make prediction. WARNING: currently it is a stub")
    fun predict(
            @RequestBody predictionData: String
    ): PredictionResponse {
        val innerList: List<Double> = listOf(0.998404324, 0.000147037499, 0.000262046466,
                0.00102976453, 0.000156891823)
        val upperList: List<List<Double>> = listOf(innerList)
        return PredictionResponse(upperList)
    }
}
