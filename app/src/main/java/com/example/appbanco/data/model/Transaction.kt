package com.example.appbanco.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    val record: TransactionData
)

@Serializable
data class TransactionData(
    val balance: Double,
    val transactions: List<Transaction>
)

@Serializable
data class Transaction(
    val id: Int,
    val name: String,
    val amount: Double,
    val date: String,
    val type: String
)