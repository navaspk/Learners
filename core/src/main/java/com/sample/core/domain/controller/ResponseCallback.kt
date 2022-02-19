package com.sample.core.domain.controller

import com.sample.core.domain.entity.BaseError

interface ResponseCallback<T> {
    fun onApiCallSuccessResponse(response: T)
    fun onApiCallError(error: BaseError)
}