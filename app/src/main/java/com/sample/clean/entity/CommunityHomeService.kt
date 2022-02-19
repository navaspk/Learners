package com.sample.clean.entity

import com.sample.clean.servermodel.CommunityLearnersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CommunityHomeService {

    @GET("api/community_1.json")
    fun getCommunityUsers(): Single<CommunityLearnersResponse>

}