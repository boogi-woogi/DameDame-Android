package com.yangbong.data.remote.service

import com.yangbong.data.remote.call_adapter.NetworkState
import com.yangbong.data.remote.model.request.LoginRequest
import com.yangbong.data.remote.model.response.BaseResponse
import com.yangbong.data.remote.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("user/login")
    suspend fun postLogin(
        @Body body: LoginRequest
    ): NetworkState<BaseResponse<LoginResponse>>
}