package com.aakash.android.di

import com.aakash.android.data.remote.PokemonListApiService
import com.aakash.android.data.repository.PokemonRepositoryImpl
import com.aakash.android.domain.repository.PokemonRepository
import com.aakash.android.domain.usecase.GetPokemonDetailUseCase
import com.aakash.android.domain.usecase.GetPokemonListUseCase
import com.aakash.android.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = Constants.BASE_URL

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Singleton
    fun provideApiService(): PokemonListApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonListApiService::class.java)

    @Provides
    @Singleton
    fun providesPokemonRepository(api: PokemonListApiService): PokemonRepository =
        PokemonRepositoryImpl(api)

}

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetPokemonListUseCase(repo: PokemonRepository): GetPokemonListUseCase =
        GetPokemonListUseCase(repo)

    @Provides
    fun providesGetPokemonDetailUseCase(repo: PokemonRepository): GetPokemonDetailUseCase =
        GetPokemonDetailUseCase(repo)
}
