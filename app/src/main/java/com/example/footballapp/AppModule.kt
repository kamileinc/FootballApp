package com.example.footballapp

import android.app.Application
import androidx.room.Room
import com.example.footballapp.data.api.RetroService
import com.example.footballapp.data.api.TeamRepository
import com.example.footballapp.data.db.data_source.TeamDatabase
import com.example.footballapp.data.db.repository.TeamRoomRepository
import com.example.footballapp.data.db.repository.TeamRoomRepositoryImpl
import com.example.footballapp.utilities.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesTeamRepository(retroService: RetroService): TeamRepository {
        return TeamRepository(retroService)
    }

    @Singleton
    @Provides
    fun providesRetrofitApi(): RetroService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetroService::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamDatabase(app: Application): TeamDatabase {
        return Room.databaseBuilder(
            app,
            TeamDatabase::class.java,
            TeamDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTeamRoomRepository(db: TeamDatabase): TeamRoomRepository {
        return TeamRoomRepositoryImpl(db.teamDao)
    }
}
