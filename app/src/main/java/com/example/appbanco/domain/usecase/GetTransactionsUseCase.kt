package com.example.appbanco.domain.usecase

import com.example.appbanco.data.model.Transaction
import com.example.appbanco.data.model.TransactionData
import com.example.appbanco.data.repository.TransactionRepository
import com.example.appbanco.domain.util.Resource
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Resource<TransactionData> {
        return repository.getTransactions()
    }
}

class CalculateTransactionSumsUseCase @Inject constructor() {

    fun calculateIncome(transactions: List<Transaction>): Double {
        return transactions
            .filter { it.type.equals("ingreso", ignoreCase = true) }
            .sumOf { it.amount }
    }

    fun calculateExpenses(transactions: List<Transaction>): Double {
        return transactions
            .filter { it.type.equals("egreso", ignoreCase = true) }
            .sumOf { kotlin.math.abs(it.amount) }
    }
}