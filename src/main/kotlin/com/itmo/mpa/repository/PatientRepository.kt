package com.itmo.mpa.repository

import com.itmo.mpa.entity.Patient
import org.springframework.data.repository.CrudRepository

interface PatientRepository : CrudRepository<Patient, Long>