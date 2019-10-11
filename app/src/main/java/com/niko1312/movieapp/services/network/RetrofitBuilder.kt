package com.niko1312.movieapp.services.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitBuilder {
    private val client = buildClient()
    private val retrofit = buildRetrofit()
    private const val TIMEOUT_SEC = 60L
    private const val BASE_URL = "https://api.themoviedb.org"
    const val BASE_IMG_URL = "https://image.tmdb.org"

    private val unsafeOkHttpClient: OkHttpClient.Builder
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                    .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                builder.addInterceptor(logging)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder.retryOnConnectionFailure(true)
                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .addConverterFactory(getConverterFactory())
            .build()
    }

    private fun getConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .create()
        )
    }

    private fun buildClient(): OkHttpClient {
        val builder = unsafeOkHttpClient
            .addInterceptor(HeaderInterceptor())
            .addInterceptor { chain ->
                var request = chain.request()
                val builder1 = request.newBuilder()
                    .addHeader("Accept", "application/json")
                request = builder1.build()
                chain.proceed(request)
            }

        return builder.build()
    }

    internal class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request()
                .newBuilder()

            return chain.proceed(builder.build())
        }
    }

    fun <T> createService(service: Class<T>): ApiService {
        return retrofit.create(service) as ApiService
    }

}
