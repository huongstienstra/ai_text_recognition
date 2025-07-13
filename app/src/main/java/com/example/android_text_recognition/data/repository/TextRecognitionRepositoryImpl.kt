package com.example.android_text_recognition.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.example.android_text_recognition.data.datasource.local.MLKitTextRecognitionDataSource
import com.example.android_text_recognition.domain.model.TextRecognitionResult
import com.example.android_text_recognition.domain.repository.TextRecognitionRepository
import javax.inject.Inject

class TextRecognitionRepositoryImpl @Inject constructor(
    private val mlKitDataSource: MLKitTextRecognitionDataSource
) : TextRecognitionRepository {
    
    override suspend fun recognizeTextFromImage(imageUri: Uri): Result<TextRecognitionResult> {
        return mlKitDataSource.recognizeTextFromUri(imageUri)
    }
    
    override suspend fun recognizeTextFromBitmap(bitmap: Bitmap): Result<TextRecognitionResult> {
        return mlKitDataSource.recognizeTextFromBitmap(bitmap)
    }
}