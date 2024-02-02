package com.example.mychat.domainLayer

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class )
object AppModule {

    @Provides
    @Singleton
    fun provideServerRepo() : ServerRepo
    {
        return ServerRepoImpl()
    }
}