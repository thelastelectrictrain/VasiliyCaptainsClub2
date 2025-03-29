package com.example.captainsclub2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.captainsclub2.data.models.Operation
import com.example.captainsclub2.ui.components.RentalCard
import com.example.captainsclub2.ui.viewmodels.MainViewModel

@Composable
fun RentalScreen(viewModel: MainViewModel = viewModel()) {
    var operations by remember { mutableStateOf<List<Operation>>(emptyList()) }
    var selectedItemId by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        operations = viewModel.getActiveOperations()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Прокат", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Выбор товара
        Row {
            Button(onClick = { selectedItemId = 1 }) { Text("Утки") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { selectedItemId = 2 }) { Text("Яхты") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { selectedItemId = 3 }) { Text("Сувениры") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список активных операций
        operations.forEach { operation ->
            RentalCard(operation = operation, onAction = { action ->
                when (action) {
                    "CANCEL" -> viewModel.cancelOperation(operation.id)
                    "COMPLETE" -> viewModel.completeOperation(operation.id)
                }
            })
        }
    }
}

@Composable
fun RentalCard(operation: Operation, onAction: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Товар: ${operation.itemType}", style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onAction("CANCEL") }) {
                Icon(Icons.Default.Close, contentDescription = "Cancel")
            }
            IconButton(onClick = { onAction("COMPLETE") }) {
                Icon(Icons.Default.Check, contentDescription = "Complete")
            }
        }
    }
}