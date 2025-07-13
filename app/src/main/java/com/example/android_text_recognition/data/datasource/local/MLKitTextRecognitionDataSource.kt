package com.example.android_text_recognition.data.datasource.local

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.android_text_recognition.domain.model.BoundingBox
import com.example.android_text_recognition.domain.model.TextBlock
import com.example.android_text_recognition.domain.model.TextRecognitionResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class MLKitTextRecognitionDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    suspend fun recognizeTextFromUri(uri: Uri): Result<TextRecognitionResult> = 
        suspendCancellableCoroutine { continuation ->
            try {
                val image = InputImage.fromFilePath(context, uri)
                recognizeText(image) { result ->
                    continuation.resume(result)
                }
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    
    suspend fun recognizeTextFromBitmap(bitmap: Bitmap): Result<TextRecognitionResult> = 
        suspendCancellableCoroutine { continuation ->
            try {
                val image = InputImage.fromBitmap(bitmap, 0)
                recognizeText(image) { result ->
                    continuation.resume(result)
                }
            } catch (e: Exception) {
                continuation.resume(Result.failure(e))
            }
        }
    
    private fun recognizeText(image: InputImage, onResult: (Result<TextRecognitionResult>) -> Unit) {
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val recognizedText = visionText.text
                
                val textBlocks = mutableListOf<TextBlock>()
                
                // Extract text blocks with their bounding boxes
                visionText.textBlocks.forEach { block ->
                    block.boundingBox?.let { blockRect ->
                        textBlocks.add(
                            TextBlock(
                                text = block.text,
                                boundingBox = BoundingBox(
                                    left = blockRect.left,
                                    top = blockRect.top,
                                    right = blockRect.right,
                                    bottom = blockRect.bottom
                                )
                            )
                        )
                    }
                    
                    // Also extract individual lines for more granular detection
                    block.lines.forEach { line ->
                        line.boundingBox?.let { lineRect ->
                            textBlocks.add(
                                TextBlock(
                                    text = line.text,
                                    boundingBox = BoundingBox(
                                        left = lineRect.left,
                                        top = lineRect.top,
                                        right = lineRect.right,
                                        bottom = lineRect.bottom
                                    )
                                )
                            )
                        }
                    }
                    
                    // Extract individual words for finest granularity (like in the UI image)
                    block.lines.forEach { line ->
                        line.elements.forEach { element ->
                            element.boundingBox?.let { elementRect ->
                                textBlocks.add(
                                    TextBlock(
                                        text = element.text,
                                        boundingBox = BoundingBox(
                                            left = elementRect.left,
                                            top = elementRect.top,
                                            right = elementRect.right,
                                            bottom = elementRect.bottom
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
                
                onResult(Result.success(
                    TextRecognitionResult(
                        fullText = recognizedText,
                        textBlocks = textBlocks.distinctBy { "${it.text}_${it.boundingBox.left}_${it.boundingBox.top}" }
                    )
                ))
            }
            .addOnFailureListener { e ->
                onResult(Result.failure(e))
            }
    }
}