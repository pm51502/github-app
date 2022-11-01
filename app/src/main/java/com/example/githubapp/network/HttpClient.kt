package com.example.githubapp.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

val httpClient: HttpClient = HttpClient(Android) {
    expectSuccess = true

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("HTTP", message)
            }
        }
        level = LogLevel.ALL
    }

    install(JsonFeature) {
        serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}
