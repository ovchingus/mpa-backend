package com.itmo.mpa.service.impl.predicate.resolver

sealed class ResolvingError(val code: Int)

sealed class UnresolvedPropertyError(code: Int, val reason: String) : ResolvingError(code)
class NoMatchedResolverError(val reference: String) : ResolvingError(ResolverErrorCode.UNKNOWN.code)
object IllegalResolvingStateError : ResolvingError(ResolverErrorCode.ILLEGAL_STATE.code)

class PatientResolvingError(reason: String) : UnresolvedPropertyError(ResolverErrorCode.PATIENT.code, reason)
class StatusResolvingError(reason: String) : UnresolvedPropertyError(ResolverErrorCode.STATUS.code, reason)
class MedicineResolvingError(reason: String) : UnresolvedPropertyError(ResolverErrorCode.MEDICINE.code, reason)
