package br.com.moneyconversionpro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.moneyconversionpro.api.RepositoryRates
import kotlinx.coroutines.Dispatchers

class CurrencyRatesViewModel : ViewModel() {
    private val repository: RepositoryRates = RepositoryRates()

    fun getCurrencies() = repository.getCurrencies()

    fun getRates(base: String) = liveData(Dispatchers.IO) {
        try {
            val retrievedData = repository.getRates(base)
            emit(retrievedData)
        } catch (e: Exception) {
            emit(Exception("Erro! Verifique sua conexão com a internet"))
        }
    }
}