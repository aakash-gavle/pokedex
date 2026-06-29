package com.aakash.android.domain.usecase

import com.aakash.android.domain.model.PokemonDetail
import com.aakash.android.domain.repository.PokemonRepository

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): PokemonDetail {
        return repository.getPokemonByName(name)
    }
}
