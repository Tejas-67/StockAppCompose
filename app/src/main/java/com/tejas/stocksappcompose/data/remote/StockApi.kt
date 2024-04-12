package com.tejas.stocksappcompose.data.remote

import com.tejas.stocksappcompose.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query")
    suspend fun getListings(
        @Query("function") function: String = FUNCTION_LISTING_STATUS,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    @GET("query")
    suspend fun getIntradayInfo(
        @Query("function") function: String = FUNCTION_TIME_SERIES_INTRADAY,
        @Query("interval") interval: String = INTERVAL_60_MIN,
        @Query("datatype") datatype: String = DATA_TYPE_CSV,
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody
    @GET("query")
    suspend fun getCompanyInfo(
        @Query("function") function: String = FUNCTION_OVERVIEW,
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyInfoDto


    companion object{
        const val INTERVAL_60_MIN = "60min"
        const val DATA_TYPE_CSV = "csv"
        const val FUNCTION_TIME_SERIES_INTRADAY = "TIME_SERIES_INTRADAY"
        const val FUNCTION_LISTING_STATUS = "LISTING_STATUS"
        const val FUNCTION_OVERVIEW = "OVERVIEW"
        const val API_KEY = "4BE6KTZEAB3JOAGH"
        const val BASE_URL = "https://www.alphavantage.co"
    }
}