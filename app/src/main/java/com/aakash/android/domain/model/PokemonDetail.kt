package com.aakash.android.domain.model

data class Ability(val name: String)

data class PokemonDetail(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val imgUrl: String,
    val abilities: List<Ability>,
    val sprites: List<String>
)
