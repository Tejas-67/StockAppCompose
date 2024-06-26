package com.tejas.stocksappcompose.domain.repository

import com.tejas.stocksappcompose.domain.model.CompanyInfo
import com.tejas.stocksappcompose.domain.model.CompanyListing
import com.tejas.stocksappcompose.domain.model.IntradayInfo
import com.tejas.stocksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}