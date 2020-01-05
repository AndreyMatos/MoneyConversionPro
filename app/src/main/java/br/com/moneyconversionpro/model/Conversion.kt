package br.com.moneyconversionpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Conversion(
    @PrimaryKey(autoGenerate = true) val _id: Int?,
    val fromCurrency: String,
    val toCurrency: String,
    val time: Long
)