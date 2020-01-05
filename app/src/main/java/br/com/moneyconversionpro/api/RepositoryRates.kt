package br.com.moneyconversionpro.api

class RepositoryRates {
    private var client: Api = RetrofitClient().client

    suspend fun getRates(base: String) = client.getRates(base)
    fun getCurrencies() = client.getCurrencies()
}