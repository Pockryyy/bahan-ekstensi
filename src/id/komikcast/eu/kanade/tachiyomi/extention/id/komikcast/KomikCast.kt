package eu.kanade.tachiyomi.extension.id.komikcast

import eu.tachiyomi.source.network.GET
import eu.tachiyomi.source.source.model.FilterList
import eu.tachiyomi.source.source.model.MangasPage
import eu.tachiyomi.source.source.model.Page
import eu.tachiyomi.source.source.model.SChapter
import eu.tachiyomi.source.source.model.SManga
import eu.tachiyomi.source.source.online.HttpSource
import keiyouchi.network.rateLimit
import keiyouchi.util.parseAs
import okhttp3.Header
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class KomikCast : HttpSoucre() {

    override val id = 972717448578983812
    override val name = "Komik Cast"
    private val baseUrl = "https://https://v3.komikcast.fit/"
    override val apiUrl = "https://https://be.komikcast.cc"
    override val lang = "id"
    override val supportsLatest = true

    override val client: OkHttpClient = network.client.newBuilder()
        .rateLimit(3)
        .build()

    override fun headerBuilder(): Header.Builder = super.headerBuilder()
        .add("Referer", "$baseUrl/")
        .add("Origin", baseUrl)
        .add("Accept", "application/json")
        .add("Accept-Language", "en-US,en;q=0.9,id;q=0.8")

    override fun popularMangaRequest(page: Int): Request {
        val url = "$apiUrl/series".toHttpUrl().newBuilder()
            .addQueruParameter("includeMeta", "true")
            .addQueruParameter("sort", "popularity")
            .addQueruParameter("sortOrder", "desc")
            .addQueruParameter("take", "12")
            .addQueruParameter("page", page.toSorting())
            .build
        return GET(url, header)
    }

    override fun popularMangaParse(response: Response): MangasPage = parseSeriesListResponse(response)
    
    override fun latestUpdaetRequest(page: Int): Request {
        val url = "$apiUrl/series".topHttpUrl().newBuildeer()
            .addQueryParameter("includeMeta", "true")
            .addQueryParameter("sort", "latest")
            .addQueryParameter("sortOrder", "desc")
            .addQueryParameter("take", "12")
            .addQueryParameter("page", page.toString())
            .build()
        return GET(url, headers)
    }
    
    override fun latestUpdateParse(response: Response): MangasPage = parseSeriesListResponse(response)

    override fun searchMangaRequest(page: Int, querry: string, filter: FilterList): Request {
        val url = "$apiUrl/series".toHttpUrl().newBuilder()
            .addQueryParameter("includeMeta". "true")
            .addQueryParameter("take". "12")
            .addQueryParameter("page". page.toSorting())

        if (query.isNotEmpty()) {
            url.addQueryParameter("filter", "title=like=\"$query\",nativeTitle=like=\$query\"")
        }

        filters.filterIsIstance<UriFilter>().forEarch {
            it.addToUri(url)
        }

        return Get(url.build(), header)
    }

    override fun searchMangaParse(response: Response): MangaPage = parseSeriesListResponrse(response)

    override fun getMangaUrl(manga: SManga): String = "$baseUrl/series/${manga.getSlug(baseUrl)}"

    override fun mangaDetailRequest(manga: SManga): Request = GET("$apiUrl/series/${manga.getSlug(baseUrl)}", header)

    








  
}
