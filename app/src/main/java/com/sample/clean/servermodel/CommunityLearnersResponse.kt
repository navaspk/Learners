package com.sample.clean.servermodel

import com.sample.core.domain.entity.BaseResponse

data class CommunityLearnersResponse(
    val response: ArrayList<ResponseItem>? = null,
    val errorCode: Any? = null,
    val type: String? = null
): BaseResponse()

data class ResponseItem(
    val firstName: String? = null,
    val referenceCnt: Int? = null,
    val pictureUrl: String? = null,
    val learns: List<String?>? = null,
    val topic: String? = null,
    val natives: List<String?>? = null,
    var like: Boolean = false
)

