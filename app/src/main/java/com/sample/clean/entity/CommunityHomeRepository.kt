package com.sample.clean.entity

import com.sample.clean.servermodel.CommunityLearnersResponse
import io.reactivex.Single
import javax.inject.Inject

class CommunityHomeRepository @Inject constructor(
    private val communityRemote: CommunityRemote
) : CommunityRepository {

    override fun getLearners(): Single<CommunityLearnersResponse> {
        return communityRemote.getLanguageLearners()
    }
}