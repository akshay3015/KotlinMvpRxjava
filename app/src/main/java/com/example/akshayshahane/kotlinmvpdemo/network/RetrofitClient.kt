package com.example.akshayshahane.kotlinmvpdemo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.CipherSuite
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec
import java.util.*


fun getRetrofitInstance (debug: Boolean = false) =
         getRetrofitClient(setLoggingInterceptor(false))




private fun setLoggingInterceptor(debug: Boolean): OkHttpClient {

    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val client = OkHttpClient.Builder()

    if (debug) {

        logger.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(logger)

    }
    val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
            .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
            .build()
            client.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
    return client.build()


}


private  fun  getRetrofitClient(client : OkHttpClient): Retrofit =
        Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .client(setLoggingInterceptor(true))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()




