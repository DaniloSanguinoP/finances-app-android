package com.example.appbanco.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appbanco.presentation.components.SummaryCard
import com.example.appbanco.presentation.components.TransactionItem
import com.example.appbanco.presentation.viewmodel.ListTransactionViewModel
import com.example.appbanco.R

@Composable
fun ListTransactionsScreen(viewModel: ListTransactionViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
        when {
            uiState.value.isLoading -> {
                LoadingScreen()
            }

            uiState.value.errorMessage != null -> {
                ErrorScreen(
                    message = stringResource(R.string.error_loading),
                    onRetry = { viewModel.loadTransactions() }
                )
            }

            else -> {
                TransactionContent(
                    uiState = uiState.value,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun TransactionContent(
    uiState: com.example.appbanco.presentation.state.TransactionUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SummaryCard(
                balance = uiState.balance,
                totalIncome = uiState.totalIncome,
                totalExpenses = uiState.totalExpenses
            )
        }

        item {
            Text(
                text = stringResource(R.string.recent_transactions),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(uiState.transactions) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}