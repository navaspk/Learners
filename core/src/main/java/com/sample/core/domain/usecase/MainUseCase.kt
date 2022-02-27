package com.sample.core.domain.usecase

import com.google.gson.Gson
import com.sample.core.CommunityLogger
import com.sample.core.domain.executor.PostExecutionThread
import com.sample.core.domain.entity.*
import com.sample.core.domain.controller.CallbackListener
import com.sample.core.extensions.TAG
import com.sample.core.extensions.empty
import com.sample.core.extensions.safeGet
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.TimeoutException

abstract class MainUseCase<T : BaseResponse, Params>(private val postExecutionThread: PostExecutionThread) {

    companion object {
        private const val SOMETHING_WENT_WRONG =
            "Something went wrong , please try again after some time"
    }

    abstract fun createUseCase(params: Params?): Single<T>

    fun execute(
        callbackListener: CallbackListener<T>?,
        params: Params? = null
    ): Disposable? {

        if (callbackListener == null) {
            return null
        }

        val single = createUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler())

        return single.subscribe({ result ->
            callbackListener.onApiCallSuccessResponse(result)
        }, { exception ->

            val baseError: BaseError
            when (exception) {
                is HttpException -> {
                    exception.response().errorBody().run {
                        val error = this?.string().safeGet()
                        CommunityLogger.e(
                            TAG,
                            "Retrofit exception $error with error code ${exception.code()}"
                        )

                        when (exception.response().code()) {
                            // 429 - Too many request code
                            429 -> callbackListener.onApiCallError(
                                BaseError(
                                    errorMessage = getErrorMessageFromResponse(error),
                                    errorCode = ResponseErrors.HTTP_TOO_MANY_REQUEST,
                                    errorBody = error
                                )
                            )
                            else -> handleResponseError(error, callbackListener)
                        }
                    }
                }

                is ServerNotAvailableException -> {
                    baseError = BaseError(
                        errorMessage = "Server not available",
                        errorCode = ResponseErrors.HTTP_UNAVAILABLE
                    )
                    callbackListener.onApiCallError(baseError)
                }

                is HTTPNotFoundException -> {
                    baseError = BaseError(
                        errorMessage = "Server not available",
                        errorCode = ResponseErrors.HTTP_NOT_FOUND
                    )
                    callbackListener.onApiCallError(baseError)
                }

                is ConnectException -> {
                    callbackListener.onApiCallError(
                        BaseError(
                            errorMessage = "Internet not available",
                            errorCode = ResponseErrors.CONNECTIVITY_EXCEPTION
                        )
                    )
                }

                is IOException,
                is TimeoutException -> {
                    baseError = if (exception.localizedMessage != null) {
                        BaseError(
                            errorMessage = exception.localizedMessage!!,
                            errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                        )
                    } else {
                        BaseError(
                            errorMessage = SOMETHING_WENT_WRONG,
                            errorCode = ResponseErrors.UNKNOWN_EXCEPTION
                        )
                    }
                    callbackListener.onApiCallError(baseError)
                }

                is HTTPBadRequest -> {
                    callbackListener.onApiCallError(
                        BaseError(
                            errorMessage = SOMETHING_WENT_WRONG,
                            errorCode = ResponseErrors.HTTP_BAD_REQUEST
                        )
                    )
                }
            }
        })

    }

    private fun getErrorMessageFromResponse(error: String): String {
        var errorMessage: String = String.empty
        try {
            val baseError = Gson().fromJson(
                error,
                BaseError::class.java
            )

            if (baseError != null) {
                baseError.apply {
                    errorMessage = if (this.message.isNotBlank()) {
                        this.message
                    } else {
                        this.errorMessage
                    }
                }
            } else {
                errorMessage = SOMETHING_WENT_WRONG
            }

        } catch (e: Exception) {
            errorMessage = SOMETHING_WENT_WRONG
        } finally {
            return errorMessage
        }
    }

    private fun handleResponseError(
        error: String,
        callbackListener: CallbackListener<T>?
    ) {
        callbackListener?.onApiCallError(
            error = BaseError(
                errorMessage = getErrorMessageFromResponse(error),
                errorCode = ResponseErrors.RESPONSE_ERROR
            )
        )
    }


}