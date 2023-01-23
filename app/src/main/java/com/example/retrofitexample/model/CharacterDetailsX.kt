package com.example.retrofitexample.model

data class CharacterDetailsX(
    val affiliation: String,
    val birthday: String,
    val constellation: String,
    val constellations: List<Constellation>,
    val description: String,
    val name: String,
    val nation: String,
    val passiveTalents: List<PassiveTalent>,
    val rarity: Int,
    val skillTalents: List<SkillTalent>,
    val title: String,
    val vision: String,
    val vision_key: String,
    val weapon: String,
    val weapon_type: String
)