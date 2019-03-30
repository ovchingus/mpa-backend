package com.itmo.mpa.model

class Patient(
        val name: String,
        val age: Int,
        var status: Status
) {
    var id: Int? = null
}