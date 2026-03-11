package com.praisetechzw.phoneguard.feature.report_stolen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.praisetechzw.phoneguard.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportStolenScreen(
    onNavigateBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel()
) {
    var imei by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var incidentDate by remember { mutableStateOf("") }
    
    val isLoading by viewModel.isLoading
    val isSuccess by viewModel.isSuccess

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Stolen", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Report Lost or Stolen Device",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            
            Text(
                text = "By reporting your device, it will be flagged globally, making it difficult to resell.",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // IMEI Field
            OutlinedTextField(
                value = imei,
                onValueChange = { if (it.length <= 15) imei = it },
                label = { Text("Device IMEI") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Field (Simple Text for now)
            OutlinedTextField(
                value = incidentDate,
                onValueChange = { incidentDate = it },
                label = { Text("Incident Date") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description of Incident") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = { viewModel.reportStolen(imei, description, incidentDate, onNavigateBack) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled = !isLoading && !isSuccess
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isSuccess) Brush.horizontalGradient(listOf(SuccessGreen, SuccessGreen))
                            else Brush.horizontalGradient(listOf(ErrorRed, Color(0xFF991B1B)))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else if (isSuccess) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Report Submitted", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Text(
                            text = "Submit Report",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Warning Note
            Card(
                colors = CardDefaults.cardColors(containerColor = WarningOrange.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = WarningOrange)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Filing a false report is a criminal offense. Please ensure the information provided is accurate.",
                        fontSize = 12.sp,
                        color = WarningOrange
                    )
                }
            }
        }
    }
}
