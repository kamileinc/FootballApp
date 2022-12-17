package com.example.footballapp.data.api

import com.example.footballapp.R
import com.example.footballapp.utilities.Constants.HTTP_ERROR
import com.example.footballapp.utilities.Resource
import com.example.footballapp.utilities.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class SafeApiCall {
    suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): Resource<T> {
        return try {
            val data = withContext(Dispatchers.IO) {
                apiCall.invoke()
            }

            Resource.Success(data)

        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> Resource.Failure(
                    UiText.DynamicString(
                        HTTP_ERROR + throwable.response()?.code()
                    ), null
                )
                is SocketTimeoutException -> Resource.Failure(
                    UiText.StringResource(resId = R.string.timeout_error),
                    null
                )
                is IOException -> Resource.Failure(
                    UiText.StringResource(resId = R.string.io_error),
                    null
                )
                else -> Resource.Failure(
                    UiText.StringResource(resId = R.string.general_error),
                    null
                )
            }
        }
    }
}
