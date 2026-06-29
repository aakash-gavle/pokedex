package com.aakash.android.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aakash.android.domain.model.PokemonOverview

class PokemonPagingSource(
    private val apiService: PokemonListApiService
) : PagingSource<Int, PokemonOverview>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonOverview>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(state.config.pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonOverview> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize

            val response = apiService.getPokemonList(limit, offset)
            val results = response.pokemons.map { dto ->
                val id = dto.url.trimEnd('/').substringAfterLast('/')
                val imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                PokemonOverview(name = dto.name, imgUrl = imgUrl)
            }

            val nextKey = if (results.isEmpty()) null else offset + limit
            val prevKey = if (offset == 0) null else offset - limit

            LoadResult.Page(
                data = results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
