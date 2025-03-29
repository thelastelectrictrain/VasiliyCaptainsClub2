package com.example.captainsclub2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.captainsclub2.ui.viewmodels.MainViewModel

@Composable
fun OpenShiftScreen(viewModel: MainViewModel, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Смена ${viewModel.getCurrentDate()}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.openShift()
                navController.navigate("main")
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Открыть смену")
        }
    }
}