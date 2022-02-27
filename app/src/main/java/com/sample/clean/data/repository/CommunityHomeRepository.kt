package com.sample.clean.data.repository

import com.sample.clean.data.entity.CommunityRemote
import com.sample.clean.data.model.CommunityLearnersResponse
import io.reactivex.Single
import javax.inject.Inject

class CommunityHomeRepository @Inject constructor(
    private val communityRemote: CommunityRemote
) : CommunityRepository {

    override fun getLearners(): Single<CommunityLearnersResponse> {
        return communityRemote.getLanguageLearners()
    }
}