package com.example.captainsclub2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.captainsclub2.data.models.Matros
import com.example.captainsclub2.repository.ShiftRepository
import com.example.captainsclub2.ui.viewmodels.MainViewModel

@Composable
fun MatrosScreen(viewModel: MainViewModel = viewModel()) {
    var matrosList by remember { mutableStateOf<List<Matros>>(emptyList()) }
    var newMatrosName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        matrosList = ShiftRepository.getAllMatros()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Матросы", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Раздел "Команда"
        Text(text = "Команда", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        matrosList.forEach { matros ->
            MatrosCard(matros = matros, onAction = { action ->
                when (action) {
                    "JOIN" -> ShiftRepository.addMatrosToShift(matros.id)
                    "REMOVE" -> ShiftRepository.removeMatros(matros)
                }
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Добавление нового матроса
        OutlinedTextField(
            value = newMatrosName,
            onValueChange = { newMatrosName = it },
            label = { Text("Имя нового матроса") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (newMatrosName.isNotBlank()) {
                ShiftRepository.addMatros(Matros(name = newMatrosName, isPermanent = true))
                newMatrosName = ""
            }
        }) {
            Text("Добавить матроса")
        }
    }
}

@Composable
fun MatrosCard(matros: Matros, onAction: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* Open dropdown menu */ },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = matros.name, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onAction("JOIN") }) {
                Icon(Icons.Default.Person, contentDescription = "Join Shift")
            }
            IconButton(onClick = { onAction("REMOVE") }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Matros")
            }
        }
    }
}