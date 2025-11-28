package com.terrapay.terrapaypalcomposesdkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.terrapay.terrapaypaypalsdk.api.TerraPayClient
import com.terrapay.terrapaypaypalsdk.api.TerraPayConfig
import com.terrapay.terrapaypaypalsdk.api.TerraPayResult
import com.terrapay.terrapaypaypalsdk.ui.HomeActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dashboard()
        }
    }
}

@Composable
fun Dashboard() {
    val context = LocalContext.current
    val activity = LocalActivity.current as ComponentActivity
    var initializeSdk by remember { mutableStateOf(false) }
    var launchTerrapaySdk by remember { mutableStateOf(false) }
    var showProgressDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf("") }

    if (showProgressDialog) {
        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Blue
                    )
                }
            }
        }
    }

    if (showErrorDialog.isNotEmpty()) {
        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(100.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp),
                        text = showErrorDialog,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(10.dp),
                        onClick = { showErrorDialog = "" },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text(text = "Dismiss")
                    }
                }
            }
        }
    }

    LaunchedEffect(initializeSdk) {
        if (initializeSdk) {
            // Configure SDK
            val config = TerraPayConfig(
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
                termsConditionsUrl = ""
            )

            TerraPayClient.init(
                activity = activity,
                context = context,
                config = config,
                onInitializeStart = {
                    showProgressDialog = true
                }
            ) { result ->
                showProgressDialog = false
                when (result) {
                    is TerraPayResult.Success -> {
                        print("Success: ${result.message}")
                        launchTerrapaySdk = true
                    }

                    is TerraPayResult.Cancelled -> {
                        print("User cancelled: ${result.message}")
                        showErrorDialog = "${result.message}"
                    }

                    is TerraPayResult.Error -> {
                        showErrorDialog = "${result.error.errorCode}\n ${result.error.message}"
                    }
                }
            }
        }
    }
    if (launchTerrapaySdk) {
        TerraPayClient.LaunchTerraPaySDK(activity, "Airtel Money Wallet") {
            launchTerrapaySdk = false
        }
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Toolbar()
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(paddingValues)
                .background(color = Color.White)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .heightIn(100.dp)
                    .padding(top = 15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(
                            horizontal = 15.dp,
                            vertical = 15.dp
                        )
                ) {
                    Text(
                        text = "Hello, John!",
                        color = Color.Gray
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .heightIn(80.dp)
                            .padding(top = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, color = Color.Gray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .heightIn(80.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Balance",
                                    color = Color.LightGray
                                )
                                Text(
                                    modifier = Modifier.padding(top = 10.dp),
                                    text = "KES 10,000",
                                    color = Color.LightGray
                                )
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            initializeSdk=true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "PayPal Services",
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Toolbar() {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(40.dp)
            .background(color = Color.White)
    ) {
        IconButton(onClick = { backPressedDispatcher!!.onBackPressed() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back Button"
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(130.dp),
            painter = painterResource(id = R.drawable.airtel_logo),
            contentDescription = "Logo",
        )
    }
}
