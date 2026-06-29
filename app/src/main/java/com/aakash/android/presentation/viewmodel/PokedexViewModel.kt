package com.aakash.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aakash.android.domain.model.PokemonDetail
import com.aakash.android.domain.model.PokemonOverview
import com.aakash.android.domain.usecase.GetPokemonDetailUseCase
import com.aakash.android.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = false,
    val info: PokemonDetail? = null,
    val error: String = ""
)

class PokedexViewModel(
    getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    val pokemons: Flow<PagingData<PokemonOverview>> = getPokemonListUseCase()
        .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getPokemonDetails(name: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val details = getPokemonDetailUseCase(name)
                _uiState.value = _uiState.value.copy(isLoading = false, info = details, error = "")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.localizedMessage ?: "Failed to load details")
            }
        }
    }
}
