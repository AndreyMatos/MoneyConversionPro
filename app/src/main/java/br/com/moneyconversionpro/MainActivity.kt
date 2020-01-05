package br.com.moneyconversionpro

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setTitle(R.string.signin_actionbar_title)
        btnLoginUserPassword.setOnClickListener(this)
        btnLoginFacebook.setOnClickListener(this)
        btnLoginTwitter.setOnClickListener(this)
        btnLoginGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //PROCESS LOGIN
//        Log.d(TAG, "go to the next activity "+v?.id)
        startActivity(Intent(this, CurrencyConversionActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }
}
