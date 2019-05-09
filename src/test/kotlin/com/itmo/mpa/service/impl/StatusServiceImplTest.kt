package com.itmo.mpa.service.impl

import com.itmo.mpa.dto.request.StatusRequest
import com.itmo.mpa.entity.DiseaseAttributeValue
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.State
import com.itmo.mpa.entity.Status
import com.itmo.mpa.repository.*
import com.itmo.mpa.service.exception.NoPendingDraftException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import java.time.Instant
import java.util.*

class StatusServiceImplTest {

    @MockK
    lateinit var patientRepository: PatientRepository

    @MockK
    lateinit var statusRepository: StatusRepository

    @MockK
    lateinit var diseaseAttributeValueRepository: DiseaseAttributeValueRepository

    @MockK
    lateinit var diseaseAttributeRepository: DiseaseAttributeRepository

    @MockK
    lateinit var attributeRepository: AttributeRepository

    @MockK
    lateinit var stateRepository: StateRepository

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
            every { patientRepository.findById(patient.id) } returns Optional.of(patient)
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

        @Nested
        @DisplayName("and draft was found")
        inner class DraftFound {

            lateinit var statusDraft: Status

            @BeforeEach
            fun setUp() {
                statusDraft = Status().apply {
                    id = 1
                    patient = this@StatusServiceImplTest.patient
                    submittedOn = Instant.now()
                    diseaseAttributeValues = emptySet()
                    draft = true
                }
                every { statusRepository.findStatusByPatientAndDraft(patient, draft = true) } returns statusDraft
                every { statusRepository.findStatusByPatientIdAndDraft(patient.id, draft = true) } returns statusDraft
                every { statusRepository.save(statusDraft) } returns statusDraft
                every { statusRepository.delete(any()) } returns Unit
            }

            @Test
            fun `on commit it turns to status`() {
                statusServiceImpl.commitDraft(patient.id)

                assertThat(statusDraft.draft, `is`(false))
                verify { statusRepository.save(statusDraft) }
            }

            @Test
            fun `rewrite replaces old draft with saves new`() {
                every { statusRepository.save(any<Status>()) } returns Status().apply { patient = this@StatusServiceImplTest.patient }
                every { stateRepository.findById(any()) } returns Optional.of(State())
                every { diseaseAttributeValueRepository.saveAll(any<Iterable<DiseaseAttributeValue>>()) } returns emptySet()

                val statusRequest = StatusRequest(stateId = 42)
                statusServiceImpl.rewriteDraft(patient.id, statusRequest)

                val statusSlot = slot<Status>()
                verify { statusRepository.save(capture(statusSlot)) }
                assertThat(statusSlot.captured.patient, `is`(patient))
            }
        }
    }
}