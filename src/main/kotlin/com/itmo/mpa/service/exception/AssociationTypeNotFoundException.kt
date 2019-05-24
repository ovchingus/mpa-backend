package com.itmo.mpa.service.exception

import com.itmo.mpa.entity.AssociationType

class AssociationTypeNotFoundException(type: String?) :
        NotFoundException("No association type: $type, " +
                "value must be in ${AssociationType.values().drop(1).map { it.name.toLowerCase() }}")