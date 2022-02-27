package com.sample.clean.data.repository

import com.sample.clean.data.model.CommunityLearnersResponse
import io.reactivex.Single

interface CommunityRepository {

    fun getLearners(): Single<CommunityLearnersResponse>

}