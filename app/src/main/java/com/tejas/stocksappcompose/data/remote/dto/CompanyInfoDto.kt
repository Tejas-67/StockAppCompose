package com.tejas.stocksappcompose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyInfoDto(
    @SerializedName("Name") val name: String?,
    @SerializedName("Symbol") val symbol: String?,
    @SerializedName("AssetType") val assetType: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Exchange") val exchange: String?,
    @SerializedName("Currency") val currency: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Sector") val sector: String?,
    @SerializedName("Industry") val industry: String?
)