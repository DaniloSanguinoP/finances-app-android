package com.example.appbanco.presentation.state

import com.example.appbanco.data.model.Transaction

data class TransactionUiState(
    val isLoading: Boolean = false,
    val balance: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val errorMessage: String? = null
)