package com.sample.clean.data.entity

import com.sample.clean.data.api.CommunityHomeServiceApi
import javax.inject.Inject

class CommunityRemote @Inject constructor(
    private val communityHomeServiceApi: CommunityHomeServiceApi
) {

    fun getLanguageLearners() = communityHomeServiceApi.getCommunityUsers()

}
