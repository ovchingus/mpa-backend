package com.itmo.mpa.service.impl

import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.service.StatusService
import com.itmo.mpa.service.exception.PatientNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class PatientServiceImplTest {

    @MockK
    lateinit var patientRepository: PatientRepository

    @MockK
    lateinit var statusService: StatusService

    @InjectMockKs
    lateinit var patientServiceImpl: PatientServiceImpl

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `when patient not found by id exception is thrown`() {
        every { patientRepository.findById(1) } returns Optional.empty()

        assertThrows<PatientNotFoundException> { patientServiceImpl.findPatient(1) }
    }
}