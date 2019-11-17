package com.itmo.mpa.service.mapping

import com.itmo.mpa.dto.response.StateImageResponse
import com.itmo.mpa.dto.response.StateResponse
import com.itmo.mpa.entity.StateImage
import com.itmo.mpa.entity.states.State
import org.springframework.web.multipart.MultipartFile
import java.io.File

fun State.toResponse() = StateResponse(
        id = this.id,
        name = this.name,
        description = this.description
)

fun StateImage.toResponse(picture: File) = StateImageResponse(
        state = this.machineState,
        algorithmPosition = this.algorithmPosition,
        picture = picture
)
