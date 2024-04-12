package com.tejas.stocksappcompose.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query")
    suspend fun getListings(
        @Query("function") function: String = FUNCTION_LISTING_STATUS,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object{
        const val FUNCTION_LISTING_STATUS = "LISTING_STATUS"
        const val API_KEY = "4BE6KTZEAB3JOAGH"
        const val BASE_URL = "https://www.alphavantage.co"
    }
}