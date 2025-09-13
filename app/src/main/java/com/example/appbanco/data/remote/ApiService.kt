package com.example.appbanco.data.remote

import com.example.appbanco.data.model.TransactionResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v3/b/687e9f36f7e7a370d1eb9ee0")
    suspend fun getTransactions(): TransactionResponse
}