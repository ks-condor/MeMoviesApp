package com.kevinserrano.apps.memoviesapp.domain.models

import com.kevinserrano.apps.memoviesapp.R

data class MovieDomain(
    val id:Long = 0L,
    val title:String = "",
    val description:String = "",
    val year: String = "2022",
    val poster: Int = R.drawable.the_conjuring_1
)