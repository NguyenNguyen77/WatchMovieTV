package com.nguyennk.movieapp.model
import com.beust.klaxon.*

private fun <T> Klaxon.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonValue) -> T, toJson: (T) -> String, isUnion: Boolean = false) =
    this.converter(object: Converter {
        @Suppress("UNCHECKED_CAST")
        override fun toJson(value: Any)        = toJson(value as T)
        override fun fromJson(jv: JsonValue)   = fromJson(jv) as Any
        override fun canConvert(cls: Class<*>) = cls == k.java || (isUnion && cls.superclass == k.java)
    })

private val klaxon = Klaxon()
    .convert(EpisodeCurrent::class, { EpisodeCurrent.fromValue(it.string!!) }, { "\"${it.value}\"" })
    .convert(Lang::class,           { Lang.fromValue(it.string!!) },           { "\"${it.value}\"" })
    .convert(Quality::class,        { Quality.fromValue(it.string!!) },        { "\"${it.value}\"" })
    .convert(Type::class,           { Type.fromValue(it.string!!) },           { "\"${it.value}\"" })

data class PhimLeResponse (
    val status: String,
    val msg: String,
    val data: DataPhimLe
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<PhimLeResponse>(json)
    }
}

data class DataPhimLe (
    val seoOnPage: SEOOnPage,
    val breadCrumb: List<BreadCrumb>,
    val titlePage: String,
    val items: List<ItemPhimLe>,
    val params: Params,

    @Json(name = "type_list")
    val typeList: String,

    @Json(name = "APP_DOMAIN_FRONTEND")
    val appDomainFrontend: String,

    @Json(name = "APP_DOMAIN_CDN_IMAGE")
    val appDomainCDNImage: String
)

data class BreadCrumb (
    val name: String,
    val slug: String? = null,
    val isCurrent: Boolean,
    val position: Long
)

data class ItemPhimLe (
    val modified: Modified,

    @Json(name = "_id")
    val id: String,

    val name: String,
    val slug: String,

    @Json(name = "origin_name")
    val originName: String,

    val type: Type,

    @Json(name = "poster_url")
    val poster_url: String,

    @Json(name = "thumb_url")
    val thumb_url: String,

    @Json(name = "sub_docquyen")
    val subDocquyen: Boolean,

    val chieurap: Boolean,
    val time: String,

    @Json(name = "episode_current")
    val episodeCurrent: EpisodeCurrent,

    val quality: Quality,
    val lang: Lang,
    val year: Long,
    val category: List<CategoryMovie>,
    val country: List<CategoryMovie>
)



enum class EpisodeCurrent(val value: String) {
    Full("Full");

    companion object {
        public fun fromValue(value: String): EpisodeCurrent = when (value) {
            "Full" -> Full
            else   -> throw IllegalArgumentException()
        }
    }
}

enum class Lang(val value: String) {
    Vietsub("Vietsub");

    companion object {
        public fun fromValue(value: String): Lang = when (value) {
            "Vietsub" -> Vietsub
            else      -> throw IllegalArgumentException()
        }
    }
}

data class Modified (
    val time: String
)

enum class Quality(val value: String) {
    Fhd("FHD"),
    HD("HD");

    companion object {
        public fun fromValue(value: String): Quality = when (value) {
            "FHD" -> Fhd
            "HD"  -> HD
            else  -> throw IllegalArgumentException()
        }
    }
}

enum class Type(val value: String) {
    Single("single");

    companion object {
        public fun fromValue(value: String): Type = when (value) {
            "single" -> Single
            else     -> throw IllegalArgumentException()
        }
    }
}

data class Params (
    @Json(name = "type_slug")
    val typeSlug: String,

    val filterCategory: List<String>,
    val filterCountry: List<String>,
    val filterYear: String,
    val filterType: String,
    val sortField: String,
    val sortType: String,
    val pagination: Pagination
)

data class Pagination (
    val totalItems: Long,
    val totalItemsPerPage: Long,
    val currentPage: Long,
    val totalPages: Long
)

data class SEOOnPage (
    @Json(name = "og_type")
    val ogType: String,

    val titleHead: String,
    val descriptionHead: String,

    @Json(name = "og_image")
    val ogImage: List<String>,

    @Json(name = "og_url")
    val ogURL: String
)
