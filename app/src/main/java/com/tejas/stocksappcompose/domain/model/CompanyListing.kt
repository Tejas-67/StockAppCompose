package com.tejas.stocksappcompose.domain.model

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String,
    var assetType: String,
    var ipoDate: String,
    var delistingDate: String,
    var status: String
)
