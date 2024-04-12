package com.tejas.stocksappcompose.data.mapper

import com.tejas.stocksappcompose.data.local.CompanyListingEntity
import com.tejas.stocksappcompose.data.remote.dto.CompanyInfoDto
import com.tejas.stocksappcompose.domain.model.CompanyInfo
import com.tejas.stocksappcompose.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(
        name = this.name,
        symbol = this.symbol,
        exchange = this.exchange,
        ipoDate = this.ipoDate,
        assetType = this.assetType,
        delistingDate = this.delistingDate,
        status = this.status
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(
        name = this.name,
        symbol = this.symbol,
        exchange = this.exchange,
        ipoDate = this.ipoDate,
        assetType = this.assetType,
        delistingDate = this.delistingDate,
        status = this.status
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        name = name?: "",
        symbol = symbol?: "",
        assetType = assetType?: "",
        description = description?: "",
        exchange = exchange?: "",
        currency = currency?: "",
        country = country?: "",
        sector = sector?: "",
        industry = industry?: ""
    )
}