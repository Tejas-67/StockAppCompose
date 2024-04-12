package com.tejas.stocksappcompose.domain.model

data class CompanyInfo(
    val name: String,
    val symbol: String,
    val assetType: String,
    val description: String,
    val exchange: String,
    val currency: String,
    val country: String,
    val sector: String,
    val industry: String
)