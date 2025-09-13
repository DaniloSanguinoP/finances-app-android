package com.example.appbanco.presenter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.appbanco.data.model.Transaction
import com.example.appbanco.data.model.TransactionData
import com.example.appbanco.domain.usecase.CalculateTransactionSumsUseCase
import com.example.appbanco.domain.usecase.GetTransactionsUseCase
import com.example.appbanco.domain.util.Resource
import com.example.appbanco.presentation.viewmodel.ListTransactionViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

// Ejecutar los test por separado

@ExperimentalCoroutinesApi
class ListTransactionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getTransactionsUseCase: GetTransactionsUseCase = mock()
    private val calculateSumsUseCase: CalculateTransactionSumsUseCase = mock()
    private lateinit var viewModel: ListTransactionViewModel

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)

        viewModel = ListTransactionViewModel(getTransactionsUseCase, calculateSumsUseCase)
    }

    @Test
    fun `when getTransactionsUseCase returns success, uiState updates correctly`() = runTest {

        val mockTransactions = listOf(
            Transaction(id = 1, name = "Salario", amount = 100.0, date = "2023-10-27", type = "ingreso"),
            Transaction(id = 2, name = "Alquiler", amount = 50.0, date = "2023-10-27", type = "egreso")
        )
        val mockData = TransactionData(balance = 50.0, transactions = mockTransactions)

        whenever(getTransactionsUseCase.invoke()).thenReturn(Resource.Success(mockData))
        whenever(calculateSumsUseCase.calculateIncome(mockTransactions)).thenReturn(100.0)
        whenever(calculateSumsUseCase.calculateExpenses(mockTransactions)).thenReturn(50.0)

        viewModel.loadTransactions()

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertEquals(50.0, uiState.balance, 0.0)
        assertEquals(100.0, uiState.totalIncome, 0.0)
        assertEquals(50.0, uiState.totalExpenses, 0.0)
        assertEquals(mockTransactions, uiState.transactions)
    }

    @Test
    fun `when getTransactionsUseCase returns error, uiState updates with error message`() = runTest {
        val errorMessage = "Error de conexión"
        whenever(getTransactionsUseCase.invoke()).thenReturn(Resource.Error(errorMessage))

        viewModel.loadTransactions()

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertEquals(errorMessage, uiState.errorMessage)
    }
}