package io.viewpoint.giphy.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GiphyApi(
    private val builder: Builder
) {
    private val debugLog: ((String) -> Unit)? = builder.debugLog

    private val apiKey: String = requireNotNull(builder.apiKey) {
        "apiKey MUST be not null"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(builder.url)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(OkHttpClient.Builder()
            .apply {
                val logMethod = debugLog
                if (logMethod != null) {
                    addInterceptor(HttpLoggingInterceptor {
                        logMethod(it)
                    }.apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }

                addInterceptor { chain ->
                    val request = chain.request()

                    chain.proceed(
                        request
                            .newBuilder()
                            .url(
                                request.url
                                    .newBuilder()
                                    .addQueryParameter("api_key", apiKey)
                                    .build()
                            )
                            .build()
                    )
                }
            }
            .build())
        .build()

    inline fun <reified T> getService() = getService(T::class.java)

    fun <T> getService(clazz: Class<T>): T = retrofit.create(clazz)

    class Builder {
        var url: String = BASE_URL
        var debugLog: ((String) -> Unit)? = null
        var apiKey: String? = null

        fun build(): GiphyApi = GiphyApi(this)
    }

    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/"
    }
}