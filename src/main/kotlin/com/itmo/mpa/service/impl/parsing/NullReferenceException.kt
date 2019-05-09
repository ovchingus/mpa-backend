package com.itmo.mpa.service.impl.parsing

class NullReferenceException(referenceName: String) : RuntimeException("Reference $referenceName resolved to null")