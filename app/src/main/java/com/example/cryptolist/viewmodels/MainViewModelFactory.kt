package com.example.cryptolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptolist.repository.CryptoRepository

class MainViewModelFactory (private val cryptoRepository: CryptoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(cryptoRepository) as T
    }
}