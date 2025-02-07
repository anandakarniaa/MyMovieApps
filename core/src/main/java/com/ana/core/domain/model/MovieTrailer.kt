package com.ana.core.domain.model

data class MovieTrailer(
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val id: String
)
