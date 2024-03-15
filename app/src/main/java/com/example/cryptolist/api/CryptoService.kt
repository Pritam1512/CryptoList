package com.example.cryptolist.api

import com.example.cryptolist.models.CryptoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoService {
    @GET("v3/ac7d7699-a7f7-488b-9386-90d1a60c4a4b")
    suspend fun getCryptoDetails() : Response<CryptoList>
}