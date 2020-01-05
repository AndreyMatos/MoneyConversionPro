package br.com.moneyconversionpro.api

import br.com.moneyconversionpro.model.RateWrapper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("latest")
    fun getCurrencies(): Call<ResponseBody>

    @GET("latest")
    suspend fun getRatesForEuro(): RateWrapper

    @GET("latest")
    suspend fun getRates(@Query("base") base: String): RateWrapper
}