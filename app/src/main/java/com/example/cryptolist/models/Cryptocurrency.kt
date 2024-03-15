package com.example.cryptolist.models

data class Cryptocurrency(
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val symbol: String,
    val type: String
)