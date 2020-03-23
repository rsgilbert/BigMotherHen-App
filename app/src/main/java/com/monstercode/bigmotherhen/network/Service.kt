package com.monstercode.bigmotherhen.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val localUrl = "http://localhost:3000/"
private const val baseUrl = ""
private const val inUseUrl = localUrl

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface Service {
    @GET("chapters")
    suspend fun fetchChapters(): NetworkChapterList
}

/**
 * Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
 */
private val service: Service by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(inUseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    retrofit.create(Service::class.java)
}

fun getNetworkService() = service
