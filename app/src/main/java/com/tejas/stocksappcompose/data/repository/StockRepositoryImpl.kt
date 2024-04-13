package com.tejas.stocksappcompose.data.repository

import android.os.Build
import android.util.Log
import com.tejas.stocksappcompose.data.csv.CSVParser
import com.tejas.stocksappcompose.data.local.StockDatabase
import com.tejas.stocksappcompose.data.mapper.toCompanyInfo
import com.tejas.stocksappcompose.data.mapper.toCompanyListing
import com.tejas.stocksappcompose.data.mapper.toCompanyListingEntity
import com.tejas.stocksappcompose.data.remote.StockApi
import com.tejas.stocksappcompose.domain.model.CompanyInfo
import com.tejas.stocksappcompose.domain.model.CompanyListing
import com.tejas.stocksappcompose.domain.model.IntradayInfo
import com.tejas.stocksappcompose.domain.repository.StockRepository
import com.tejas.stocksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.tejas.stocksappcompose.util.Resource.Success

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
): StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        Log.w("http", "called: getCompanyListings in repo")
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            Log.w("http", "local: $localListings")
            emit(Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            Log.w("http", "repo shouldJustLoadFromCache: $shouldJustLoadFromCache")
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try{
                val response = api.getListings()
                Log.w("http", response.byteStream().toString())
                companyListingParser.parse(response.byteStream())
            }catch(e: Exception){
                e.printStackTrace()
                emit(Resource.Error("some error occurred"))
                null
            }
            Log.w("http", remoteListings.toString())
            remoteListings?.let{listings->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Success(
                    data = dao.searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try{
            val response = api.getIntradayInfo(symbol = symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        }catch(e: Exception){
            e.printStackTrace()
            Resource.Error("couldn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>{
        return try {
            val response = api.getCompanyInfo(symbol = symbol)
            Log.w("state", "repo: $response")
            Log.w("state", "repo: info: ${response.toCompanyInfo()}")
            Resource.Success(response.toCompanyInfo())
        }catch(e: Exception){
            e.printStackTrace()
            Resource.Error("couldn't load company info")
        }
    }


}