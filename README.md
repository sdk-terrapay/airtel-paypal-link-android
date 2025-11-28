# 📦 TerraPaySDK

TerraPaySDK is a lightweight and customizable SDK that allows seamless integration of TerraPay wallet services into your Android app. It provides support for linking PayPal wallet, top-up, and withdrawal flows with configurable UI elements like wallet branding and color themes.

## 🚀 Features

- Set wallet metadata like name, logo, and user info.
- Configure UI labels and brand colors.
- Launch SDK with a single entry point.
- Easily embeddable into any Android app.

## 📲 Requirements

- Android Studio
- AGP 8.5.0
- compileSdk 35

## 🔧 Installation

1. Create or open an Android project.
2. Copy the `terrapaypaypalsdk-release.aar` file from the sources folder.
3. Create a `libs` folder under the app root folder and place the `.aar` file inside.

   ```
   TerraPaySDK/
       └── app
           └── libs
               └── terrapaypaypalsdk-release.aar
   ```

4. Add the following dependencies in `build.gradle`:

   ```gradle
   implementation(files("libs/terrapaypaypalsdk-release.aar"))
   implementation(platform("androidx.compose:compose-bom:2025.04.01"))
   implementation("com.google.accompanist:accompanist-swiperefresh:0.36.0")
   implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
   implementation("androidx.activity:activity-compose:1.10.1")
   implementation("androidx.core:core-ktx:1.15.0")
   implementation("androidx.datastore:datastore-preferences:1.1.5")
   implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
   implementation("androidx.navigation:navigation-compose:2.8.9")
   implementation("io.github.grizzi91:bouquet:1.1.2")
   implementation("com.squareup.retrofit2:converter-gson:2.11.0")
   implementation("com.google.code.gson:gson:2.11.0")
   implementation("junit:junit:4.13.2")
   implementation("androidx.test.ext:junit:1.2.1")
   implementation("androidx.test.espresso:espresso-core:3.6.1")
   implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
   implementation("androidx.activity:activity-compose:1.10.1")
   implementation("androidx.compose:compose-bom:2025.02.00")
   implementation("androidx.compose.ui:ui")
   implementation("androidx.compose.ui:ui-graphics")
   implementation("androidx.compose.ui:ui-tooling")
   implementation("androidx.compose.ui:ui-tooling-preview")
   implementation("androidx.compose.ui:ui-test-junit4")
   implementation("androidx.compose.material3:material3")
   implementation("androidx.appcompat:appcompat:1.7.0")
   implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
   implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
   implementation("com.google.android.material:material:1.12.0")
   implementation("com.squareup.retrofit2:retrofit:2.11.0")
   implementation("com.intuit.sdp:sdp-android:1.1.1")
   implementation("com.github.Kaaveh:sdp-compose:1.1.0")
   implementation("androidx.fragment:fragment-ktx:1.8.6")
   implementation("com.intuit.ssp:ssp-android:1.1.1")
   implementation("androidx.browser:browser:1.8.0")
   ```

## 🛠️ Usage

### 1. Initialize and Configure - Compose

```kotlin
if (initializeSdk) {
    TerraPay.init(
        owner = activity,
        context = context,
        walletName = "Airtel Wallet",
        msisdn = "+254792474539",
        walletLogo = null,
        primaryColor = "EC1B24",
        secondaryColor = "FFFFFF",
        topUpLabel = "Top-up",
        withdrawLabel = "Withdrawal",
        onInitializeStart = {
            // Show progress until linking happens in the background
        }
    ) { success, response, message ->
        if (success) {
            launchSdk = true
        } else {
            // Show the proper error message returned
        }
    }
}

if (launchSdk) {
    LaunchTerraPaySDK(activity, true) {
        onDismiss()
    }
}
```

### 2. Initialize and Configure - Views

```kotlin
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
            // Show progress until linking happens in the background
        },
    ) { success, linkUserResponse, message ->
        if (success) {
            TerraPay.launchTerraPaySDK(this, linkUserResponse)
        } else {
            // Show the proper error message
        }
    }
}
```

## 🔐 License

This SDK is proprietary and intended for internal or authorized use only. For licensing, contact TerraPay.

## 📬 Contact

For support or inquiries, email: support@terrapay.com

---