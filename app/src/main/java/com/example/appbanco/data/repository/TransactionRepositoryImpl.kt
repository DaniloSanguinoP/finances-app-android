package com.example.appbanco.data.repository

import com.example.appbanco.data.model.TransactionData
import com.example.appbanco.domain.util.Resource

interface TransactionRepository {
    suspend fun getTransactions(): Resource<TransactionData>
}