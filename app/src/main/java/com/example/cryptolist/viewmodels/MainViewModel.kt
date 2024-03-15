package com.example.cryptolist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptolist.models.CryptoList
import com.example.cryptolist.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val cryptoRepository: CryptoRepository) : ViewModel(){

    init {
        viewModelScope.launch (Dispatchers.IO){
            cryptoRepository.getCrypto(1)
        }
    }

    val crypto : LiveData<CryptoList>
        get() = cryptoRepository.crypto
}