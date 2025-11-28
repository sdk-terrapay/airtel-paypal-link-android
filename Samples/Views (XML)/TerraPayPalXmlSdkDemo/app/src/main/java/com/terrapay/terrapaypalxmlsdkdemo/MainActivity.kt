package com.terrapay.terrapaypalxmlsdkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.terrapay.terrapaypaypalsdk.api.TerraPay

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
        findViewById<MaterialButton>(R.id.launch_sdk).setOnClickListener {
            TerraPay.init(
                owner = activity,
                context = this,
                walletName = "Airtel Wallet",
                msisdn = "+254792474545",
                walletLogo = null,
                primaryColor = "EC1B24",
                secondaryColor = "FFFFFF",
                topUpLabel = "Add Money",
                withdrawLabel = "Get Money",
                onInitializeStart = {

                },
            ) { success, linkUserResponse, message ->
                if (success) {
                    TerraPay.launchTerraPaySDK(this, linkUserResponse)
                }
            }
        }
    }
}