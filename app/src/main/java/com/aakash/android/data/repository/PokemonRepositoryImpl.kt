package com.aakash.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aakash.android.data.remote.PokemonListApiService
import com.aakash.android.data.remote.PokemonPagingSource
import com.aakash.android.domain.model.Ability
import com.aakash.android.domain.model.PokemonDetail
import com.aakash.android.domain.model.PokemonOverview
import com.aakash.android.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val apiService: PokemonListApiService
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<PokemonOverview>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { PokemonPagingSource(apiService) }
        ).flow
    }

    override suspend fun getPokemonByName(name: String): PokemonDetail {
        val dto = apiService.getPokemonByName(name)
        
        val spritesList = mutableListOf<String>()
        dto.sprites?.let {
            it.frontDefault?.let { url -> spritesList.add(url) }
            it.backDefault?.let { url -> spritesList.add(url) }
            it.frontShiny?.let { url -> spritesList.add(url) }
            it.backShiny?.let { url -> spritesList.add(url) }
        }

        return PokemonDetail(
            name = dto.name,
            baseExperience = dto.baseExperience ?: 0,
            height = dto.height ?: 0,
            weight = dto.weight ?: 0,
            imgUrl = dto.sprites?.frontDefault ?: "",
            abilities = dto.abilities?.map { Ability(it.ability?.name ?: "Unknown") } ?: emptyList(),
            sprites = spritesList
        )
    }
}
