package com.itmo.mpa.service.exception


class ImageForStateIdNotFound(id: Long) : NotFoundException("Image for State $id not found")
