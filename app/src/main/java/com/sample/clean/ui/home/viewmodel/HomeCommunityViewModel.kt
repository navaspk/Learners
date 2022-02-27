package com.sample.clean.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.clean.base.BaseNavigator
import com.sample.clean.base.BaseViewModel
import com.sample.clean.data.entity.CommunityHomeUseCase
import com.sample.clean.data.model.CommunityLearnersResponse
import com.sample.clean.data.model.ResponseItem
import com.sample.core.domain.entity.BaseError
import com.sample.core.domain.controller.CallbackListener
import com.sample.core.util.PreferenceManager
import com.sample.core.util.PreferenceManager.Companion.LIKED_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * VM class responsible for making API call and getting response & update the UI using LiveData
 */
@HiltViewModel
class HomeCommunityViewModel @Inject constructor(
    private val useCase: CommunityHomeUseCase
) : BaseViewModel<BaseNavigator>() {

    // region VARIABLES

    var liveData = MutableLiveData<ArrayList<ResponseItem>>()

    @Inject
    lateinit var preferenceManager: PreferenceManager

    // endregion

    // region UTIL

    fun getLearnersList() {
        addDisposable(
            useCase.execute(
                HomeCommunitySubscriber()
            )
        )
    }

    // endregion

    inner class HomeCommunitySubscriber : CallbackListener<CommunityLearnersResponse>() {
        override fun onApiCallSuccessResponse(response: CommunityLearnersResponse) {

            viewModelScope.launch(Dispatchers.IO) {
                if (response.response?.isEmpty()?.not() == true) {
                    val list = response.response
                    if (preferenceManager.getStringPolicy(LIKED_ITEM).isNotEmpty()) {
                        val index = preferenceManager.getStringPolicy(LIKED_ITEM).split("#")
                        if (index.isNullOrEmpty().not()) {
                            for (i in index.indices) {
                                if (index[i].isEmpty().not())
                                    list[index[i].toInt()].like = true
                            }
                        }
                    }


                    liveData.postValue(list)
                }
            }
        }

        override fun onApiCallError(error: BaseError) {
            // TODO: if network is not there then display toast
        }

    }
}