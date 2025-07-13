package com.example.android_text_recognition.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_text_recognition.domain.model.TextRecognitionResult
import com.example.android_text_recognition.domain.usecase.RecognizeTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextRecognitionViewModel @Inject constructor(
    private val recognizeTextUseCase: RecognizeTextUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TextRecognitionUiState())
    val uiState: StateFlow<TextRecognitionUiState> = _uiState.asStateFlow()
    
    fun recognizeTextFromImage(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            recognizeTextUseCase(uri).fold(
                onSuccess = { result ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recognitionResult = result,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
    
    fun recognizeTextFromBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true, 
                error = null,
                capturedImage = bitmap
            )
            
            recognizeTextUseCase(bitmap).fold(
                onSuccess = { result ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recognitionResult = result,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
    
    fun clearResult() {
        _uiState.value = _uiState.value.copy(
            recognitionResult = null,
            capturedImage = null,
            error = null
        )
    }
}

data class TextRecognitionUiState(
    val isLoading: Boolean = false,
    val recognitionResult: TextRecognitionResult? = null,
    val capturedImage: Bitmap? = null,
    val error: String? = null
)