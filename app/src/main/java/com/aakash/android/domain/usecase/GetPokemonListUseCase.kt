package com.aakash.android.domain.usecase

import androidx.paging.PagingData
import com.aakash.android.domain.model.PokemonOverview
import com.aakash.android.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<PokemonOverview>> {
        return repository.getPokemonList()
    }
}
