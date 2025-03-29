package com.example.captainsclub2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.captainsclub2.data.models.Operation

@Composable
fun RentalCard(
    item: Operation,
    onAction: (String) -> Unit
) {
    Card(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp))
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Утка ${item.itemId}", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${item.startTime} - ${item.endTime}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onAction("CANCEL") }) {
                    Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Color.Red)
                }
                IconButton(onClick = { onAction("COMPLETE") }) {
                    Icon(Icons.Default.Check, contentDescription = "Complete", tint = Color.Green)
                }
            }
        }
    }
}