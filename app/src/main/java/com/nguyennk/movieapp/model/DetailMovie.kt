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
    val serverName: String,

    @Json(name = "server_data")
    val serverData: List<ServerDatum>
)

data class ServerDatum (
    val name: String,
    val slug: String,
    val filename: String,

    @Json(name = "link_embed")
    val linkEmbed: String,

    @Json(name = "link_m3u8")
    val linkM3U8: String
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
    val type: String,
    val status: String,

    @Json(name = "poster_url")
    val posterURL: String,

    @Json(name = "thumb_url")
    val thumbURL: String,

    @Json(name = "is_copyright")
    val isCopyright: Boolean,

    @Json(name = "sub_docquyen")
    val subDocquyen: Boolean,

    val chieurap: Boolean,

    @Json(name = "trailer_url")
    val trailerURL: String,

    val time: String,

    @Json(name = "episode_current")
    val episodeCurrent: String,

    @Json(name = "episode_total")
    val episodeTotal: String,

    val quality: String,
    val lang: String,
    val notify: String,
    val showtimes: String,
    val year: Long,
    val view: Long,
    val actor: List<String>,
    val director: List<String>,
    val category: List<CategoryMovie>,
    val country: List<CategoryMovie>
)


data class Created (
    val time: String
)
