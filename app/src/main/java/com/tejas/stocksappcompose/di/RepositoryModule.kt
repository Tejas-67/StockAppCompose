package com.tejas.stocksappcompose.di

import com.tejas.stocksappcompose.data.csv.CSVParser
import com.tejas.stocksappcompose.data.csv.CompanyListingParser
import com.tejas.stocksappcompose.data.repository.StockRepositoryImpl
import com.tejas.stocksappcompose.domain.model.CompanyListing
import com.tejas.stocksappcompose.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
       stockRepository: StockRepositoryImpl
    ): StockRepository

}