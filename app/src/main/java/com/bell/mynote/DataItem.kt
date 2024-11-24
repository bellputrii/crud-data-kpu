package com.bell.mynote

import java.io.Serializable

data class DataItem(
    val id: Int,
    val nama: String,
    val nik: String,
    val gender: String,
    val alamat: String
) : Serializable


