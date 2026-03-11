package com.praisetechzw.phoneguard.feature.imei_check

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.praisetechzw.phoneguard.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImeiCheckScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: ImeiCheckViewModel = hiltViewModel()
) {
    var imei by remember { mutableStateOf("") }
    val state by viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Device", fontWeight = FontWeight.Bold) },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "IMEI Verification",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "Enter the 15-digit IMEI number of the device to check its official status.",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = imei,
                onValueChange = { if (it.length <= 15) imei = it },
                label = { Text("Enter IMEI Number") },
                placeholder = { Text("e.g. 356789012345678") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.checkImei(imei) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                enabled = state !is ImeiCheckState.Loading
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.horizontalGradient(PrimaryGradient)),
                    contentAlignment = Alignment.Center
                ) {
                    if (state is ImeiCheckState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Verify Status",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Result Area
            AnimatedVisibility(
                visible = state is ImeiCheckState.Success || state is ImeiCheckState.Error,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                when (val result = state) {
                    is ImeiCheckState.Success -> VerificationResultCard(result.result)
                    is ImeiCheckState.Error -> ErrorCard(result.message)
                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun VerificationResultCard(result: com.praisetechzw.phoneguard.core.network.DeviceStatusResponse) {
    val isClean = result.status == "clean"
    val cardColor = if (isClean) SuccessGreen else ErrorRed
    val icon = if (isClean) Icons.Default.CheckCircle else Icons.Default.Cancel
    val statusText = if (isClean) "CLEAN" else "STOLEN / LOST"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor.copy(alpha = 0.05f)),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardColor.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = cardColor,
                modifier = Modifier.size(60.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "DEVICE STATUS: $statusText",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = cardColor
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = cardColor.copy(alpha = 0.1f))

            ResultRow("Brand", result.brand ?: "Unknown")
            ResultRow("Model", result.model ?: "Unknown")
            ResultRow("IMEI", result.imei)
            if (result.reportDate != null) {
                ResultRow("Report Date", result.reportDate)
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 14.sp)
        Text(text = value, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun ErrorCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterHorizontally,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Error, contentDescription = null, tint = ErrorRed)
            Text(text = message, color = ErrorRed, fontSize = 14.sp)
        }
    }
}
