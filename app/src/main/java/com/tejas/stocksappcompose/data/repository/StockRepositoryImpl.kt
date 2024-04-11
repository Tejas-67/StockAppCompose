package com.tejas.stocksappcompose.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.tejas.stocksappcompose.data.local.StockDatabase
import com.tejas.stocksappcompose.data.mapper.toCompanyListing
import com.tejas.stocksappcompose.data.remote.StockApi
import com.tejas.stocksappcompose.domain.model.CompanyListing
import com.tejas.stocksappcompose.domain.repository.StockRepository
import com.tejas.stocksappcompose.util.Resource
import com.tejas.stocksappcompose.util.Resource.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl(
    val api: StockApi,
    val db: StockDatabase
): StockRepository {

    private val dao = db.dao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try{
                val response = api.getListings()
                
            }catch(e: IOException){
                e.printStackTrace()
                emit(Resource.Error("some error occurred"))
            }catch(e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("some error occurred"))
            }
        }
    }

}