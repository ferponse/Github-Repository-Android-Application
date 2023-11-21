package com.technical_challenge.github.data.di

import android.content.Context
import androidx.paging.Pager
import androidx.room.Room
import com.technical_challenge.github.android_utils.di.ApplicationContext
import com.technical_challenge.github.data.interceptor.AuthInterceptor
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.GithubRepositoryDao
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.remote.GithubApiClient
import com.technical_challenge.github.data.utils.Constants.BASE_URL
import com.technical_challenge.github.domain.repository.GithubRepository
import com.technical_challenge.github.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object GithubNetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesGithubApiClient(retrofit: Retrofit): GithubApiClient {
        return retrofit.create(GithubApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(context, GithubDatabase::class.java, "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}