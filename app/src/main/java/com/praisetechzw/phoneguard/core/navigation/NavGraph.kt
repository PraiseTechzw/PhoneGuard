package com.praisetechzw.phoneguard.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.praisetechzw.phoneguard.feature.auth.LoginScreen
import com.praisetechzw.phoneguard.feature.auth.RegisterScreen
import com.praisetechzw.phoneguard.feature.dashboard.DashboardScreen
import com.praisetechzw.phoneguard.feature.history.HistoryScreen
import com.praisetechzw.phoneguard.feature.imei_check.ImeiCheckScreen
import com.praisetechzw.phoneguard.feature.onboarding.OnboardingScreen
import com.praisetechzw.phoneguard.feature.profile.ProfileScreen
import com.praisetechzw.phoneguard.feature.report_stolen.ReportStolenScreen
import com.praisetechzw.phoneguard.feature.settings.SettingsScreen
import com.praisetechzw.phoneguard.feature.splash.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(onNavigateNext = { route ->
                navController.navigate(route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToImeiCheck = { navController.navigate(Screen.ImeiCheck.route) },
                onNavigateToReportStolen = { navController.navigate(Screen.ReportStolen.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = Screen.ImeiCheck.route) {
            ImeiCheckScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = Screen.ReportStolen.route) {
            ReportStolenScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = Screen.History.route) {
            HistoryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
