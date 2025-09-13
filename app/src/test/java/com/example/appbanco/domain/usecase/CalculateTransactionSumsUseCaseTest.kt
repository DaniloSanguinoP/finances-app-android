package com.example.appbanco.domain.usecase

import com.example.appbanco.data.model.Transaction
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateTransactionSumsUseCaseTest {

    private lateinit var useCase: CalculateTransactionSumsUseCase

    @Before
    fun setUp() {
        useCase = CalculateTransactionSumsUseCase()
    }

    @Test
    fun `calculateIncome should return sum of all income transactions`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Salario", 1500.0, "2024-01-15", "ingreso"),
            Transaction(2, "Freelance", 500.0, "2024-01-20", "ingreso"),
            Transaction(3, "Supermercado", -120.0, "2024-01-22", "egreso"),
            Transaction(4, "Bonus", 200.0, "2024-01-25", "ingreso")
        )

        // When
        val result = useCase.calculateIncome(transactions)

        // Then
        assertEquals(2200.0, result, 0.01)
    }

    @Test
    fun `calculateIncome should return zero when no income transactions`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Supermercado", -120.0, "2024-01-22", "egreso"),
            Transaction(2, "Gasolina", -50.0, "2024-01-23", "egreso")
        )

        // When
        val result = useCase.calculateIncome(transactions)

        // Then
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `calculateExpenses should return sum of absolute values of expense transactions`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Salario", 1500.0, "2024-01-15", "ingreso"),
            Transaction(2, "Supermercado", -120.0, "2024-01-22", "egreso"),
            Transaction(3, "Gasolina", -50.0, "2024-01-23", "egreso"),
            Transaction(4, "Restaurante", -80.75, "2024-01-24", "egreso")
        )

        // When
        val result = useCase.calculateExpenses(transactions)

        // Then
        assertEquals(250.75, result, 0.01)
    }

    @Test
    fun `calculateExpenses should return zero when no expense transactions`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Salario", 1500.0, "2024-01-15", "ingreso"),
            Transaction(2, "Bonus", 200.0, "2024-01-25", "ingreso")
        )

        // When
        val result = useCase.calculateExpenses(transactions)

        // Then
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun `calculateIncome should be case insensitive for transaction type`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Salario", 1500.0, "2024-01-15", "INGRESO"),
            Transaction(2, "Freelance", 500.0, "2024-01-20", "Ingreso"),
            Transaction(3, "Bonus", 200.0, "2024-01-25", "ingreso")
        )

        // When
        val result = useCase.calculateIncome(transactions)

        // Then
        assertEquals(2200.0, result, 0.01)
    }

    @Test
    fun `calculateExpenses should be case insensitive for transaction type`() {
        // Given
        val transactions = listOf(
            Transaction(1, "Supermercado", -120.0, "2024-01-22", "EGRESO"),
            Transaction(2, "Gasolina", -50.0, "2024-01-23", "Egreso"),
            Transaction(3, "Restaurante", -80.75, "2024-01-24", "egreso")
        )

        // When
        val result = useCase.calculateExpenses(transactions)

        // Then
        assertEquals(250.75, result, 0.01)

    }
}
