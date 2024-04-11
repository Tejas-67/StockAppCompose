package com.tejas.stocksappcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    var name: String,
    var symbol: String,
    var exchange: String,
    var assetType: String,
    var ipoDate: String,
    var delistingDate: String,
    var status: String,

    @PrimaryKey var id: Int? = null
)