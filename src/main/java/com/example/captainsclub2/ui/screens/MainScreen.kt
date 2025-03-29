package com.example.captainsclub2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.captainsclub2.ui.viewmodels.MainViewModel
import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFF5F5F5),
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.height(64.dp)
            ) {
                Text(text = "Смена ${viewModel.getCurrentDate()}", fontSize = 14.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = viewModel.getCurrentTime(), fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    viewModel.closeShift()
                    viewModel.sendReportByEmail(context, "recipient@example.com")
                }) {
                    Text("Завершить смену")
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "rental",
            modifier = Modifier.padding(padding)
        ) {
            composable("rental") { RentalScreen(viewModel) }
            composable("report") { ReportScreen(viewModel) }
            composable("matros") { MatrosScreen(viewModel) }
        }
    }
}