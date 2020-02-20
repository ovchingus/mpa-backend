package com.itmo.mpa.repository

import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class TensorflowRepository {
    private val restTemplate: RestTemplate = RestTemplate()

    fun cnnPrediction(body: Any): String? {
        return restTemplate.postForObject("http://cnn:8501/v1/models/cnn_model:predict",
                body, String::class.java)
    }
}