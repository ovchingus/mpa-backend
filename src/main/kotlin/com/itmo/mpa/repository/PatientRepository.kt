package com.itmo.mpa.repository

import com.itmo.mpa.model.Patient

interface PatientRepository : CrudRepository<Patient, Int>