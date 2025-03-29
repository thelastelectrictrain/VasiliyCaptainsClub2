package com.example.captainsclub2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.captainsclub2.data.models.Operation
import com.example.captainsclub2.ui.viewmodels.MainViewModel

@Composable
fun ReportScreen(viewModel: MainViewModel = viewModel()) {
    var operations by remember { mutableStateOf<List<Operation>>(emptyList()) }

    LaunchedEffect(Unit) {
        operations = viewModel.getActiveOperations()
    }

    // Подсчет статистики
    val totalRentals = operations.count { it.type == "RENTAL" && it.status == "COMPLETED" }
    val duckRentals = operations.count { it.type == "RENTAL" && it.itemType == "DUCK" && it.status == "COMPLETED" }
    val yachtRentals = operations.count { it.type == "RENTAL" && it.itemType == "YACHT" && it.status == "COMPLETED" }
    val fines = operations.count { it.type == "FINE" }
    val souvenirs = operations.count { it.type == "SALE" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Отчет", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Общая статистика
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Сдач: $totalRentals", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Уток: $duckRentals", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Яхт: $yachtRentals", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Штрафов: $fines", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Сувениров: $souvenirs", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Хронологический список операций
        LazyColumn {
            items(operations) { operation ->
                ReportCard(operation = operation)
            }
        }
    }
}

@Composable
fun ReportCard(operation: Operation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Утка ${operation.itemId}", style = MaterialTheme.typography.titleMedium)
            Text(text = "${operation.startTime} - ${operation.endTime}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Статус: ${operation.status}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}