package com.itmo.mpa.service.impl

import com.itmo.mpa.entity.Patient
import com.itmo.mpa.repository.PatientRepository
import com.itmo.mpa.repository.StatusRepository
import com.itmo.mpa.service.AttributeService
import com.itmo.mpa.service.exception.NoPendingDraftException
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.*
import java.time.Instant

class StatusServiceImplTest {

    @MockK
    lateinit var patientRepository: PatientRepository

    @MockK
    lateinit var statusRepository: StatusRepository

    @MockK
    lateinit var attributeService: AttributeService

    @MockK
    lateinit var patientStatusEntityService: PatientStatusEntityService

    @InjectMockKs
    lateinit var statusServiceImpl: StatusServiceImpl

    lateinit var patient: Patient

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        patient = Patient().apply {
            id = 1
            name = "name"
            birthDate = Instant.now()
        }
    }

    @Nested
    @DisplayName("when patient was found")
    inner class PatientFound {

        @BeforeEach
        fun setUp() {
            every { patientStatusEntityService.findPatient(patient.id) } returns patient
            every { patientRepository.save(patient) } returns patient
        }

        @Nested
        @DisplayName("and no draft was found")
        inner class NoDraftFound {

            @BeforeEach
            fun setUp() {
                every { statusRepository.findStatusByPatientIdAndDraft(patient.id, draft = true) } returns null
            }

            @Test
            fun `exception is thrown`() {
                assertThrows<NoPendingDraftException> { statusServiceImpl.commitDraft(patient.id) }
            }
        }
    }
}