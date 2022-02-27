package com.sample.clean.data.entity

import com.sample.clean.data.repository.CommunityRepository
import com.sample.clean.data.model.CommunityLearnersResponse
import com.sample.core.domain.usecase.MainUseCase
import com.sample.core.domain.executor.PostExecutionThread
import io.reactivex.Single
import javax.inject.Inject

class CommunityHomeUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
    postExecutionThread: PostExecutionThread
) : MainUseCase<CommunityLearnersResponse, CommunityHomeUseCase.Params>(postExecutionThread) {

    override fun createUseCase(params: Params?): Single<CommunityLearnersResponse> {
        return communityRepository.getLearners()
    }

    data class Params constructor(val request: String = "")
}