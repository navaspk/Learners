package com.sample.clean.entity

import com.sample.clean.servermodel.CommunityLearnersResponse
import io.reactivex.Single

interface CommunityRepository {

    fun getLearners(): Single<CommunityLearnersResponse>

}