package com.example.android_text_recognition.domain.model

data class TextRecognitionResult(
    val fullText: String,
    val textBlocks: List<TextBlock> = emptyList()
)

data class TextBlock(
    val text: String,
    val boundingBox: BoundingBox,
    val confidence: Float? = null
)

data class BoundingBox(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)