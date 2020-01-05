package br.com.moneyconversionpro

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.moneyconversionpro.localdb.AppDatabase
import br.com.moneyconversionpro.model.Conversion
import br.com.moneyconversionpro.model.RateWrapper
import br.com.moneyconversionpro.viewmodel.CurrencyRatesViewModel
import kotlinx.android.synthetic.main.activity_currency_conversion.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CurrencyConversionActivity : AppCompatActivity() {

    lateinit var viewModel: CurrencyRatesViewModel
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_conversion)
        db = AppDatabase.getInstance(applicationContext)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.currency_conversion_title)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(CurrencyRatesViewModel::class.java)
        btnConvert.setOnClickListener {
            convertCurrencies()
        }

//        viewModel.getCurrencies().enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(
//                    this@CurrencyConversionActivity,
//                    R.string.currency_conversion_error,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val json: JSONObject = JSONObject(response.body().toString())
//
//            }
//        })

//        viewModel.getRates("BRL").observe(this, Observer {
//            val usdVal: Double = it.rates.javaClass.getMethod("getUSD").invoke(it.rates) as Double
////            var methodNames = ""
////            for (m:Method in it.rates.javaClass.methods)
////                methodNames += m.name + " "
//            Log.d("CurrencyConversion", usdVal.toString())
//        })

    }

    private fun convertCurrencies() {
        toggleLoading()
        val valor: Double =
            if (etCurrencyOrigin.text.isEmpty()) 0.0 else etCurrencyOrigin.text.toString().toDouble()
        val base: String = spCurrencyOrigin.selectedItem.toString()
        val destination: String = spCurrencyDestination.selectedItem.toString()
        viewModel.getRates(base).observe(this, Observer {
            when (it) {
                is RateWrapper -> {
                    val rate
                            : Double =
                        it.rates.javaClass.getMethod("get$destination").invoke(it.rates) as Double
                    val initial = "$base %.2f".format(valor).replace(".", ",")
                    val converted = "$destination %.2f".format(valor * rate).replace(".", ",")
                    etCurrencyDestination.setText(converted)
                    val conversion = Conversion(
                        null,
                        initial,
                        converted,
                        Calendar.getInstance().timeInMillis
                    )
                    GlobalScope.launch {
                        db.conversionDao().insert(conversion)
                    }
                }
                is Exception -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            toggleLoading()
        })
    }

    private fun toggleLoading() {
        btnConvert.visibility =
            if (btnConvert.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        progressBar.visibility =
            if (btnConvert.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_currency_conversion, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
