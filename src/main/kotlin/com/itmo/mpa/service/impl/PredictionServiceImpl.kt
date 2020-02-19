package com.itmo.mpa.service.impl

import com.itmo.mpa.service.PredictionService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@Service
class PredictionService: PredictionService {

    private val restTemplate = RestTemplate()

    override fun makePrediction(csvData: String): String {
        print(csvData.split("\n").size)
        val valueArr = csvData.split("\n")[1].split(",")
        val instancesList = Collections.singletonList(
                valueArr.stream()
                .map { str -> str.toDouble()}
                .map { numb -> Collections.singletonList(numb) }
                .collect(Collectors.toList())
        )

        val requestBody = HashMap<String, Any>()
        requestBody.put("instances", instancesList)
        val response = restTemplate.postForObject("http://cnn:8501/v1/models/cnn_model:predict",
                requestBody, String::class.java)
        if(response != null) {
            return response
        }
        return ""
    }


}