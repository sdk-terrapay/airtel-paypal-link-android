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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.terrapay.terrapaypaypalsdk.api.TerraPay
import com.terrapay.terrapaypaypalsdk.ui.screens.LaunchTerraPaySDK
import com.terrapay.terrapaypaypalsdk.ui.theme.Colors
import com.terrapay.terrapaypaypalsdk.ui.theme.Dimens
import com.terrapay.terrapaypaypalsdk.ui.theme.TextStyles
import ir.kaaveh.sdpcompose.sdp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var initializeSdk by remember { mutableStateOf(false) }
            var launchSdk by remember { mutableStateOf(false) }

            Dashboard(
                initializeSdk,
                launchSdk,
                onInitialize = {
                    initializeSdk = true
                },
                onInitializationStart = {
                    // Show progress bar here
                },
                onInitializationSuccess = {
                    // If linking is successful, success callback will be returned and launch the sdk
                    initializeSdk = false
                    launchSdk = true
                },
                onInitializationFail = {
                    // Show error dialog with returned error message
                    initializeSdk = false
                },
                onDismiss = {
                    launchSdk = false
                }
            )
        }
    }
}

@Composable
fun Dashboard(
    initializeSdk: Boolean,
    launchSdk: Boolean,
    onInitialize: () -> Unit,
    onInitializationStart: () -> Unit,
    onInitializationSuccess: () -> Unit,
    onInitializationFail: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val activity = LocalActivity.current as ComponentActivity

    LaunchedEffect(initializeSdk) {
        if (initializeSdk) {
            TerraPay.init(
                owner = activity,
                context = context,
                walletName = "Airtel Money",
                msisdn = "+254792474539",
                walletLogo = null,
                primaryColor = "EC1B24",
                secondaryColor = "FFFFFF",
                topUpLabel = "Top-up",
                withdrawLabel = "Withdrawal",
                onInitializeStart = {
                    onInitializationStart()
                }
            ) { success, response, message ->
                if (success) {
                    onInitializationSuccess()
                } else {
                    onInitializationFail(message)
                }
            }
        }
    }

    if (launchSdk) {
        LaunchTerraPaySDK(activity, true) {
            onDismiss()
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
                .background(color = Colors.Background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .heightIn(100.sdp)
                    .padding(top = Dimens.PaddingLarge),
                colors = CardDefaults.cardColors(
                    containerColor = Colors.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(
                            horizontal = Dimens.HorizontalVerticalPadding,
                            vertical = Dimens.HorizontalVerticalPadding
                        )
                ) {
                    Text(
                        text = "Hello, John!",
                        style = TextStyles.roboto18pxBold,
                        color = Colors.DarkGrey
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .heightIn(80.sdp)
                            .padding(top = Dimens.PaddingLarge),
                        colors = CardDefaults.cardColors(
                            containerColor = Colors.White
                        ),
                        shape = RoundedCornerShape(Dimens.CardRadiusNormal),
                        border = BorderStroke(2.dp, color = Colors.DarkGrey)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .heightIn(80.sdp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(Dimens.PaddingNormal),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Balance",
                                    style = TextStyles.roboto18pxBold,
                                    color = Colors.MediumGrey
                                )
                                Text(
                                    modifier = Modifier.padding(top = Dimens.PaddingNormal),
                                    text = "KES 10,000",
                                    style = TextStyles.roboto20pxBold,
                                    color = Colors.MediumGrey
                                )
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .padding(top = Dimens.PaddingXXXL)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onInitialize()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.DarkGrey,
                            contentColor = Colors.White
                        ),
                        shape = RoundedCornerShape(Dimens.CardRadiusNormal)
                    ) {
                        Text(
                            modifier = Modifier.padding(Dimens.PaddingNormal),
                            text = "PayPal Services",
                            style = TextStyles.roboto14pxBold,
                            color = Colors.White
                        )
                    }
                    Spacer(modifier = Modifier.padding(Dimens.PaddingNormal))
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
            .height(40.sdp)
            .background(color = Colors.Background)
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
                .size(130.sdp),
            painter = painterResource(id = R.drawable.airtel_logo),
            contentDescription = "Logo",
        )
    }
}
