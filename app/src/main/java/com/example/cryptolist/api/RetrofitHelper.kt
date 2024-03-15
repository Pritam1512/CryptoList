package com.example.cryptolist.api

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://run.mocky.io/";

    fun getInstance() : Retrofit{
        Log.i("CL_RetrofitHelper","getInstance")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}