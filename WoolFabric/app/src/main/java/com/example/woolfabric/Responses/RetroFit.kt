package com.example.woolfabric.Responses

import android.util.Base64
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFit {
    private val AUTH="Base64"+Base64.encodeToString("sss".toByteArray(),Base64.NO_WRAP)
    private const val BASICURL="https://woolfabric.me/WoolFabric/"
val okhttps=okhttp3.OkHttpClient.Builder()
    .addInterceptor { chain->
val request=chain.request()
        val requestbuli=request.newBuilder()
            .method(request.method(),request.body())
            .addHeader("Authorization", AUTH)
            val build=requestbuli.build()
chain.proceed(build)
    }.build()
val instance :Api by lazy {
val retrofit=Retrofit.Builder()
    .baseUrl(BASICURL)
    .client(okhttps)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
retrofit.create(Api::class.java)
}
}