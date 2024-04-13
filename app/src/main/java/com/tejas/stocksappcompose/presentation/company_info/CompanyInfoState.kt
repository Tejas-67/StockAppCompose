package com.tejas.stocksappcompose.presentation.company_info

import com.tejas.stocksappcompose.domain.model.CompanyInfo
import com.tejas.stocksappcompose.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
