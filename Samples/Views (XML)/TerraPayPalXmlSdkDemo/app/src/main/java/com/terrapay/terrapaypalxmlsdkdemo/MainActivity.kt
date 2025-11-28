package com.terrapay.terrapaypalxmlsdkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.terrapay.terrapaypaypalsdk.api.TerraPay
import com.terrapay.terrapaypaypalsdk.api.TerraPayResult

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val activity = this as ComponentActivity
        val config = com.terrapay.terrapaypaypalsdk.api.TerraPayConfig(
            walletName = "Airtel Money Wallet",
            dialCode = "+254",
            countryCode = "KE",
            msisdn = "783453672",
            currency = "KES",
            email = "gajendra@gmail.com",
            primaryColor = "EC1B24",
            secondaryColor = "FFFFFF",
            addMoneyLabel = "Top Up",
            getMoneyLabel = "Withdraw",
            termsConditionsUrl=""
        )
        findViewById<Button>(R.id.button).text = "Launch SDK"
        findViewById<Button>(R.id.button).setOnClickListener {
            TerraPayClient.init(
                activity = activity,
                context = this,
                config = config,
                onInitializeStart = {
                    findViewById<ProgressBar>(R.id.progress).visibility=View.VISIBLE
                },
            ) {result ->
                findViewById<ProgressBar>(R.id.progress).visibility= View.GONE
                when (result) {
                    is TerraPayResult.Success -> {
                        TerraPayClient.launchTerraPaySDK(this)
                    }
                    is TerraPayResult.Cancelled -> {
                        showDialog(this, "User cancelled:", result.message)
                    }
                    is TerraPayResult.Error -> {
                        showDialog(this, "Error:", result.error.message ?: "Unknown error")
                    }

                }
            }
        }
    }
    fun showDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}