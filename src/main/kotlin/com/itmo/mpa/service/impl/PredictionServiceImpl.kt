package com.itmo.mpa.service.impl

import com.itmo.mpa.repository.TensorflowRepository
import com.itmo.mpa.service.PredictionService
import org.springframework.stereotype.Service

@Service
class PredictionServiceImpl(
        private val tensorflowRepository: TensorflowRepository
): PredictionService {

    override fun makePrediction(csvData: String): String {
        val splittedData = csvData.split("\n")
        if(splittedData.size < 2) {
            return "Error: Data malformed, expected CSV file of 2 lines, found " + splittedData.size
        }
        val valueArr = splittedData[1].split(",")
        val instancesList : List<List<List<Double>>> = listOf(
                valueArr.map { listOf(it.toDouble())}
        )

        val requestBody = mapOf("instances" to instancesList)
        return tensorflowRepository.cnnPrediction(requestBody)?: "Error: Tensorflow could not make prediction " +
        "on provided data"
    }
}
