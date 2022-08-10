package com.kevinserrano.apps.memoviesapp.di

import com.kevinserrano.apps.memoviesapp.BuildConfig
import com.kevinserrano.apps.memoviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.kevinserrano.apps.memoviesapp.data.remote.PelisVideosLiteApi
import com.kevinserrano.apps.memoviesapp.data.repository.MoviesRepositoryImpl
import com.kevinserrano.apps.memoviesapp.domain.datasource.MovieRemoteDataSource
import com.kevinserrano.apps.memoviesapp.domain.repository.MoviesRepository
import com.kevinserrano.apps.memoviesapp.domain.usecase.GetMoviesUseCase
import com.kevinserrano.apps.memoviesapp.network.httpClient
import com.kevinserrano.apps.memoviesapp.network.retrofitClient
import com.kevinserrano.apps.memoviesapp.utilities.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider =
        object : CoroutineContextProvider {
            override val io: CoroutineContext
                get() = Dispatchers.IO
            override val computation: CoroutineContext
                get() = Dispatchers.Default
            override val main: CoroutineContext
                get() = Dispatchers.Main
        }

    @Provides
    fun providerGetMoviesUserCase(moviesRepository: MoviesRepository): GetMoviesUseCase{
        return GetMoviesUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun providerMovieLocalDataSource(api:PelisVideosLiteApi): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(api)
    }

    @Provides
    fun providerMoviesRepository(movieRemoteDataSource: MovieRemoteDataSource): MoviesRepository {
        return MoviesRepositoryImpl(movieRemoteDataSource)
    }

    //Network
    @Provides
    @Singleton
    fun provideAPIClient(): OkHttpClient {
        return httpClient(BuildConfig.DEBUG)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return retrofitClient(baseUrl = BuildConfig.BASE_URL,httpClient = okHttpClient)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(PelisVideosLiteApi::class.java)
}