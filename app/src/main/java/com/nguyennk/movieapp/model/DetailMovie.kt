package com.nguyennk.movieapp.model

import com.beust.klaxon.*

private val klaxon = Klaxon()
data class DetailMovie (
    val status: Boolean,
    val msg: String,
    val movie: Movie,
    val episodes: List<Episode>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<DetailMovie>(json)
    }
}

data class Episode (
    @Json(name = "server_name")
    val server_name: String,

    @Json(name = "server_data")
    val server_data: List<ServerDatum>
)

data class ServerDatum (
    @Json(name = "name")
    val name: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "filename")
    val filename: String,

    @Json(name = "link_embed")
    val link_embed: String,

    @Json(name = "link_m3u8")
    val link_m3u8: String
)

data class Movie (
    val created: Created,
    val modified: Created,

    @Json(name = "_id")
    val id: String,

    val name: String,
    val slug: String,

    @Json(name = "origin_name")
    val originName: String,

    val content: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "status")
    val status: String,

    @Json(name = "poster_url")
    val poster_url: String,

    @Json(name = "thumb_url")
    val thumb_url: String,

    @Json(name = "is_copyright")
    val isCopyright: Boolean,

    @Json(name = "sub_docquyen")
    val subDocquyen: Boolean,

    val chieurap: Boolean,

    @Json(name = "trailer_url")
    val trailerURL: String,
    @Json(name = "time")
    val time: String,

    @Json(name = "episode_current")
    val episode_current: String,

    @Json(name = "episode_total")
    val episode_total: String,
    @Json(name = "quality")
    val quality: String,
    val lang: String,
    val notify: String,
    val showtimes: String,
    val year: Long,
    val view: Long,
    @Json(name = "actor")
    val actor: List<String>,
    @Json(name = "director")
    val director: List<String>,
    @Json(name = "category")
    val category: List<CategoryMovie>,
    @Json(name = "country")
    val country: List<CategoryMovie>
)


data class Created (
    val time: String
)
