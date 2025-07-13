package com.example.android_text_recognition

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.android_text_recognition.presentation.navigation.AppNavigation
import com.example.android_text_recognition.ui.theme.AndroidtextrecognitionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enhanced edge-to-edge for Android 15
        enableEdgeToEdge()
        
        // Additional setup for proper edge-to-edge on Android 15
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
        setContent {
            AndroidtextrecognitionTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    AppNavigation(paddingValues = paddingValues)
                }
            }
        }
    }
}