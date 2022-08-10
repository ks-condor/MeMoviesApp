package com.kevinserrano.apps.memoviesapp.data.models


import com.google.gson.annotations.SerializedName

data class MovieDBResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val movies: List<MovieDB> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) {
    data class MovieDB(
        @SerializedName("adult")
        val adult: Boolean = false,
        @SerializedName("backdrop_path")
        val backdropPath: String = "",
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("original_language")
        val originalLanguage: String = "",
        @SerializedName("original_title")
        val originalTitle: String = "",
        @SerializedName("overview")
        val overview: String = "",
        @SerializedName("popularity")
        val popularity: Double = 0.0,
        @SerializedName("poster_path")
        val posterPath: String = "",
        @SerializedName("release_date")
        val releaseDate: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("video")
        val video: Boolean = false,
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0,
        @SerializedName("vote_count")
        val voteCount: Int = 0
    ){
        fun toMovie(): Movie =
            Movie(
                id = id,
                title = title,
                description = overview,
                releaseDate = releaseDate,
                backdropPath = BASE_BACKDROP_PATH + backdropPath
            )
    }
}

private const val BASE_BACKDROP_PATH = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/"