package com.yangbong.damedame.di

import android.app.Application
import android.util.DisplayMetrics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(app: Application) = app

    @Provides
    fun provideDisplayMetrics(app: Application): DisplayMetrics = app.resources.displayMetrics
}
