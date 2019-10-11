package com.niko1312.movieapp.modules.main.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String? = "",
    @SerializedName("release_date") val relDate: String? = "",
    @SerializedName("poster_path") val poster: String,
    var isFav: Boolean = false
)