package com.example.appbanco.domain.repository

import com.example.appbanco.data.model.TransactionData
import com.example.appbanco.data.remote.ApiService
import com.example.appbanco.data.repository.TransactionRepository
import com.example.appbanco.domain.util.Resource
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun getTransactions(): Resource<TransactionData> {
        return try {
            val response = apiService.getTransactions()
            Resource.Success(response.record)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error desconocido")
        }
    }
}