package com.itmo.mpa.dto.response

import java.io.File

data class StateImageResponse(val state: String, val algorithmPosition: String, val picture: File)