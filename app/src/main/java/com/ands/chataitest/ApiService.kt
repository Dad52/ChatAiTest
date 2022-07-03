package com.ands.chataitest

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dad52(Sobolev) on 7/2/2022.
 */
interface ApiService {



    @GET("/get?bid=167569&key=417R2yx2ikmnomgP")
    suspend fun getAnswer(
        @Query("uid") uid: String,
        @Query("msg") msg: String
    ): Response<AnswerDTO>

}

//http://api.brainshop.ai/get?bid=167569&key=417R2yx2ikmnomgP&uid=Dad52&msg=Yeah