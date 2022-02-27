package com.sample.clean.data.api

import com.sample.clean.data.model.CommunityLearnersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CommunityHomeServiceApi {

    @GET("api/community_1.json")
    fun getCommunityUsers(): Single<CommunityLearnersResponse>

}