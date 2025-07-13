package com.example.android_text_recognition.di

import com.example.android_text_recognition.data.repository.TextRecognitionRepositoryImpl
import com.example.android_text_recognition.domain.repository.TextRecognitionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    
    @Binds
    @Singleton
    abstract fun bindTextRecognitionRepository(
        textRecognitionRepositoryImpl: TextRecognitionRepositoryImpl
    ): TextRecognitionRepository
}