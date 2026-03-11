package com.praisetechzw.phoneguard.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Settings : Screen("settings")
    object ImeiCheck : Screen("imei_check")
    object ReportStolen : Screen("report_stolen")
    object History : Screen("history")
    object Profile : Screen("profile")
}
