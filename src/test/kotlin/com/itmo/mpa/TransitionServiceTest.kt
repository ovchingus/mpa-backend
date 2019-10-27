package com.itmo.mpa

import com.itmo.mpa.dto.response.AvailableTransitionResponse
import com.itmo.mpa.dto.response.PredicateErrorResponse
import com.itmo.mpa.entity.Patient
import com.itmo.mpa.entity.Status
import com.itmo.mpa.entity.attributes.Attribute
import com.itmo.mpa.entity.attributes.DiseaseAttribute
import com.itmo.mpa.entity.attributes.DiseaseAttributeValue
import com.itmo.mpa.entity.states.State
import com.itmo.mpa.entity.states.Transition
import com.itmo.mpa.repository.TransitionRepository
import com.itmo.mpa.service.TransitionService
import com.itmo.mpa.service.impl.entityservice.PatientStatusEntityService
import com.itmo.mpa.service.impl.predicate.resolver.ResolverErrorCode
import com.itmo.mpa.service.mapping.toResponse
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Instant

@SpringBootTest
class TransitionServiceTest {

    @MockBean
    private lateinit var transitionRepository: TransitionRepository

    @MockBean
    private lateinit var patientStatusEntityService: PatientStatusEntityService

    @Autowired
    private lateinit var transitionService: TransitionService

    lateinit var patient: Patient

    lateinit var statusDraft: Status

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        patient = Patient().apply patient@{
            id = 1L
            name = "name"
            birthDate = Instant.now()
            currentStatus = Status().apply {
                id = 1L
                patient = this@patient
                submittedOn = Instant.now()
                draft = false
                state = State().apply {
                    id = 1L
                    name = "initial"
                    description = "initial state"
                }
            }
        }

        statusDraft = Status().apply draft@{
            id = 2L
            patient = this@TransitionServiceTest.patient
            submittedOn = Instant.now()
            diseaseAttributeValues = setOf(
                    DiseaseAttributeValue().apply {
                        id = 1L
                        value = "200"
                        status = this@draft
                        diseaseAttribute = DiseaseAttribute().apply {
                            attribute = Attribute().apply {
                                id = 1L
                                name = "attr1"
                                type = "number"
                            }
                        }
                    }
            )
        }
        `when`(patientStatusEntityService.findDraftWithPatient(patient.id)).thenReturn(statusDraft to patient)
    }

    @Nested
    @DisplayName("For resolvable predicate")
    inner class ResolvablePredicates {

        private val resolvedTransition = Transition().apply {
            stateTo = State().apply {
                id = 2L
                name = ""
                description = ""
            }
            predicate = "gt({status.1}, 42)"
        }

        @BeforeEach
        fun setUp() {
            `when`(transitionRepository.findByStateFrom(patient.currentStatus.state)).thenReturn(listOf(resolvedTransition))
        }

        @Test
        fun `Transition is resulted in a value`() {
            val availableTransitions = transitionService.getAvailableTransitions(patient.id)

            assertThat(availableTransitions,
                    contains(
                            AvailableTransitionResponse(
                                    state = resolvedTransition.stateTo.toResponse(),
                                    isRecommended = true,
                                    errors = null
                            )
                    )
            )
        }
    }

    @Nested
    @DisplayName("For unresolvable predicate")
    inner class UnresolvablePredicates {

        private val resolvedTransition = Transition().apply {
            stateTo = State().apply {
                id = 2L
                name = ""
                description = ""
            }
            predicate = "and(has({medicine.absurd}, 1234), or(eq({status.1101}, 1), and(lt({patient.prop}, foo), lt({no_resolver.prop}, baz)))"
        }

        @BeforeEach
        fun setUp() {
            `when`(transitionRepository.findByStateFrom(patient.currentStatus.state)).thenReturn(listOf(resolvedTransition))
        }

        @Test
        fun `Transition is resulted in null with errors collected`() {
            val availableTransition = transitionService.getAvailableTransitions(patient.id)
                    .first()

            assertThat(availableTransition.isRecommended, `is`(null as Boolean?))

            assertThat(availableTransition.errors,
                    containsInAnyOrder(
                            PredicateErrorResponse(
                                    code = ResolverErrorCode.MEDICINE.code,
                                    reason = "absurd"
                            ),
                            PredicateErrorResponse(
                                    code = ResolverErrorCode.STATUS.code,
                                    reason = "1101"
                            ),
                            PredicateErrorResponse(
                                    code = ResolverErrorCode.PATIENT.code,
                                    reason = "prop"
                            ),
                            PredicateErrorResponse(
                                    code = ResolverErrorCode.UNKNOWN.code,
                                    reason = "no_resolver.prop"
                            )
                    )
            )
        }
    }
}
