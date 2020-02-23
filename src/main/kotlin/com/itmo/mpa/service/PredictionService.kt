package com.itmo.mpa.service

interface PredictionService {

    /**
     * Requests prediction from TensorFlow for the given data
     * @param csvData raw csv data
     * @return response from TensorFlow
     */
    fun makePrediction(csvData: String): String
}
