package com.praisetechzw.phoneguard.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.praisetechzw.phoneguard.core.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateNext: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val startDestination = viewModel.startDestination.collectAsState()

    LaunchedEffect(key1 = startDestination.value) {
        if (startDestination.value != null) {
            delay(2000L) // Minimum splash time for branding
            onNavigateNext(startDestination.value!!)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "PhoneGuard",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
