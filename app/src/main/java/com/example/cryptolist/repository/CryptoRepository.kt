package com.example.cryptolist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptolist.api.CryptoService
import com.example.cryptolist.models.CryptoList

class CryptoRepository(private val cryptoService: CryptoService) {


    private val cryptoLiveData = MutableLiveData<CryptoList>()

    val crypto : LiveData<CryptoList>
    get() = cryptoLiveData

    suspend fun getCrypto(page: Int){
        val result = cryptoService.getCryptoDetails()

        Log.i("CL_CryptoRepository", "getCrypto: getCrypto")
        if(result?.body() != null){
            cryptoLiveData.postValue(result.body())
        }else{
            Log.i("CL_CryptoRepository", "result is null")
        }
    }
}