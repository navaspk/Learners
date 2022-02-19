package com.sample.clean.entity

import javax.inject.Inject

class CommunityRemote @Inject constructor(
    private val communityHomeService: CommunityHomeService
) {

    fun getLanguageLearners() = communityHomeService.getCommunityUsers()

}
