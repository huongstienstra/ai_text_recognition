package com.example.android_text_recognition.domain.repository

import android.net.Uri
import com.example.android_text_recognition.domain.model.TextRecognitionResult

interface TextRecognitionRepository {
    suspend fun recognizeTextFromImage(imageUri: Uri): Result<TextRecognitionResult>
    suspend fun recognizeTextFromBitmap(bitmap: android.graphics.Bitmap): Result<TextRecognitionResult>
}