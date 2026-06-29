package com.aakash.android.data.remote

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val count: Int,
    @SerializedName("results")
    val pokemons: List<PokemonDTO>
)

data class PokemonDTO(
    val name: String,
    val url: String
)

data class PokemonDetailDTO(
    val name: String,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    val height: Int?,
    val weight: Int?,
    val sprites: SpritesDTO?,
    val abilities: List<AbilityDTO>?
)

data class SpritesDTO(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("back_shiny")
    val backShiny: String?
)

data class AbilityDTO(
    val ability: AbilityNameDTO?
)

data class AbilityNameDTO(
    val name: String?
)
