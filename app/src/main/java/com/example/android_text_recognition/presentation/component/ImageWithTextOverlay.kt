package com.example.android_text_recognition.presentation.component

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import com.example.android_text_recognition.domain.model.TextBlock

@Composable
fun ImageWithTextOverlay(
    bitmap: Bitmap,
    textBlocks: List<TextBlock>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Display the image
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Captured Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
        
        // Draw bounding boxes over the image
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Calculate scale factors to match the image display size
            val imageAspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val canvasAspectRatio = size.width / size.height
            
            val (scaleX, scaleY, offsetX, offsetY) = if (imageAspectRatio > canvasAspectRatio) {
                // Image is wider, scale based on width
                val scale = size.width / bitmap.width
                val scaledHeight = bitmap.height * scale
                val yOffset = (size.height - scaledHeight) / 2
                listOf(scale, scale, 0f, yOffset)
            } else {
                // Image is taller, scale based on height
                val scale = size.height / bitmap.height
                val scaledWidth = bitmap.width * scale
                val xOffset = (size.width - scaledWidth) / 2
                listOf(scale, scale, xOffset, 0f)
            }
            
            // Draw bounding boxes for each text block
            textBlocks.forEach { textBlock ->
                drawBoundingBox(
                    boundingBox = textBlock.boundingBox,
                    scaleX = scaleX,
                    scaleY = scaleY,
                    offsetX = offsetX,
                    offsetY = offsetY
                )
            }
        }
    }
}

private fun DrawScope.drawBoundingBox(
    boundingBox: com.example.android_text_recognition.domain.model.BoundingBox,
    scaleX: Float,
    scaleY: Float,
    offsetX: Float,
    offsetY: Float
) {
    val left = boundingBox.left * scaleX + offsetX
    val top = boundingBox.top * scaleY + offsetY
    val right = boundingBox.right * scaleX + offsetX
    val bottom = boundingBox.bottom * scaleY + offsetY
    
    drawRect(
        color = Color.Red,
        topLeft = Offset(left, top),
        size = Size(right - left, bottom - top),
        style = Stroke(width = 3f)
    )
}