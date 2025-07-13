package com.example.android_text_recognition.domain.usecase

import android.net.Uri
import com.example.android_text_recognition.domain.model.TextRecognitionResult
import com.example.android_text_recognition.domain.repository.TextRecognitionRepository
import javax.inject.Inject

class RecognizeTextUseCase @Inject constructor(
    private val repository: TextRecognitionRepository
) {
    suspend operator fun invoke(imageUri: Uri): Result<TextRecognitionResult> {
        return repository.recognizeTextFromImage(imageUri)
    }
    
    suspend operator fun invoke(bitmap: android.graphics.Bitmap): Result<TextRecognitionResult> {
        return repository.recognizeTextFromBitmap(bitmap)
    }
}