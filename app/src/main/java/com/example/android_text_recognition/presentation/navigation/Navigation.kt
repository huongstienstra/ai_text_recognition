package com.example.android_text_recognition.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_text_recognition.presentation.screen.TextRecognitionScreen

@Composable
fun AppNavigation(
    paddingValues: PaddingValues,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TEXT_RECOGNITION
    ) {
        composable(Routes.TEXT_RECOGNITION) {
            TextRecognitionScreen(paddingValues = paddingValues)
        }
    }
}

object Routes {
    const val TEXT_RECOGNITION = "text_recognition"
}