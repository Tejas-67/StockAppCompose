package com.tejas.stocksappcompose.util

sealed class Resource<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T): Resource<T>(data, null)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>(null)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)
}