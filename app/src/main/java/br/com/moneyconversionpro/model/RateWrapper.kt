package br.com.moneyconversionpro.model

data class RateWrapper(
    val rates: Rate,
    val base: String,
    val date: String
)