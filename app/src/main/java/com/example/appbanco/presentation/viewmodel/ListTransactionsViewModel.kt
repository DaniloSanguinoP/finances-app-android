package com.example.appbanco.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbanco.domain.usecase.CalculateTransactionSumsUseCase
import com.example.appbanco.domain.usecase.GetTransactionsUseCase
import com.example.appbanco.domain.util.Resource
import com.example.appbanco.presentation.state.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTransactionViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val calculateSumsUseCase: CalculateTransactionSumsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            when (val result = getTransactionsUseCase()) {
                is Resource.Success -> {
                    val data = result.data
                    val totalIncome = calculateSumsUseCase.calculateIncome(data.transactions)
                    val totalExpenses = calculateSumsUseCase.calculateExpenses(data.transactions)

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        balance = data.balance,
                        totalIncome = totalIncome,
                        totalExpenses = totalExpenses,
                        transactions = data.transactions
                    )
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
}