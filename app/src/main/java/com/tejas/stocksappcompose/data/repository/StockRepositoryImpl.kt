package com.tejas.stocksappcompose.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.tejas.stocksappcompose.data.csv.CSVParser
import com.tejas.stocksappcompose.data.local.StockDatabase
import com.tejas.stocksappcompose.data.mapper.toCompanyListing
import com.tejas.stocksappcompose.data.mapper.toCompanyListingEntity
import com.tejas.stocksappcompose.data.remote.StockApi
import com.tejas.stocksappcompose.domain.model.CompanyListing
import com.tejas.stocksappcompose.domain.repository.StockRepository
import com.tejas.stocksappcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListing>
): StockRepository {

    private val dao = db.dao

//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        Log.w("http", "called: getCompanyListings in repo")
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            Log.w("http", "local: $localListings")
            emit(Resource.Success(
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
//            catch(e: HttpException){
//                e.printStackTrace()
//                emit(Resource.Error("some error occurred"))
//                null
//            }
            remoteListings?.let{listings->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

}