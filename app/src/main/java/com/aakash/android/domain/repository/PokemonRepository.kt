package com.aakash.android.domain.repository

import androidx.paging.PagingData
import com.aakash.android.domain.model.PokemonDetail
import com.aakash.android.domain.model.PokemonOverview
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonOverview>>
    suspend fun getPokemonByName(name: String): PokemonDetail
}
