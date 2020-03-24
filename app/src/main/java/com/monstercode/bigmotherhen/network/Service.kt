package com.monstercode.bigmotherhen.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val localUrl = "http://10.0.2.2:3000"
private const val baseUrl = ""
private const val inUseUrl = localUrl

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface Service {
    @GET("chapters")
    suspend fun fetchChapters(): List<NetworkChapter>
}

/**
 * Main entry point for network access. Call like `DevByteNetwork.devbytes.getPlaylist()`
 */
private val service: Service by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl(inUseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(Service::class.java)
}

fun getNetworkService() = service
