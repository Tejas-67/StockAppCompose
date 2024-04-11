package com.tejas.stocksappcompose.presentation.company_listings

import com.tejas.stocksappcompose.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val searchQuery: String = ""
)
