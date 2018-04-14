package com.overdrive.foodintake.server.request

import com.overdrive.foodintake.server.responce.SignInResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Overdrive on 14.04.2018.
 */
interface SignIn {
    @POST("/api/signin")
    fun signIn(@Query("name") userName: String, @Query("password") password: String, @Query("device") deviceId: String)
            : Call<List<SignInResponse>>
}