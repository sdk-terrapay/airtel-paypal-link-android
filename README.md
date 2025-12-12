# 📦 TerraPaySDK

TerraPaySDK is a lightweight and customizable SDK that allows seamless integration of TerraPay
wallet services into your Android app. It provides support for linking PayPal wallet, top-up, and
withdrawal flows with configurable UI elements like wallet branding and color themes.

## 🚀 Features

- Set wallet metadata like name, logo, and user info.
- Configure UI labels and brand colors.
- Launch SDK with a single entry point.
- Easily embeddable into any Android app.

## 📲 Requirements

- Android Studio
- AGP 8.13.0
- minSdk = 24
- compileSdk 35
- targetSdk = 35

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
4. Add the following updated version dependencies in `build.gradle`:

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
   implementation("androidx.webkit:webkit:1.11.0")
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
```kotlin
## params validation should be like below
@param context The Android context, ComponentActivity - typically an Activity or Application context.
@param walletName-	Must not be empty
@param dialCode - Must match the pattern ^\+\d+$
@param countryCode - Must be a valid ISO 3166-1 alpha-2 country code
@param msisdn - Must contain only digits; length validated against country-specific rules
@param subscriberName - Must not be empty
@param email - Must be a valid email address when not empty
@param currency - Must be a valid ISO 4217 currency code
@param primaryColor-	Must be a valid 6-digit hex code (e.g., EC1B24)
@param secondaryColor-	Must be a valid 6-digit hex code(e.g., FFFFFF) 
@param termsConditionsUrl - must be valid terms & conditions url or keep empty 
```

### 1. Initialize and Configure - Compose

```kotlin

val context = LocalContext.current
val activity = LocalActivity.current as ComponentActivity

if (showBottomSheet) {
   // Configure SDK
   val config = TerraPayConfig(
      walletName = "Airtel Money Wallet",
      dialCode = "+254",
      countryCode = "KE",
      msisdn = "783453672",
      subscriberName = "Gajendra",
      currency = "KES",
      email = "gajendra@gmail.com",
      primaryColor = "EC1B24",
      secondaryColor = "FFFFFF",
      addMoneyLabel = "Top Up",
      getMoneyLabel = "Withdraw",
      termsConditionsUrl="https://www.terrapay.com/termsconditions/"
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
            launchSdk = true
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

if (launchSdk) {
      TerraPayClient.LaunchTerraPaySDK(activity, "Airtel Money Wallet") {
         launchTerrapaySdk = false
      }
}

```

### 2. Initialize and Configure - XML(View)

```kotlin

val activity = this as ComponentActivity
val config =TerraPayConfig(
    walletName = "Airtel Money Wallet",
    dialCode = "+254",
    countryCode = "KE",
    msisdn = "783453672",
    subscriberName = 'Gajendra'
    currency = "KES",
    email = "gajendra@gmail.com",
    primaryColor = "EC1B24",
    secondaryColor = "FFFFFF",
    addMoneyLabel = "Top Up",
    getMoneyLabel = "Withdraw",
   termsConditionsUrl="https://www.terrapay.com/termsconditions/"
)
findViewById<Button>(R.id.button).text = "Launch SDK"
findViewById<Button>(R.id.button).setOnClickListener {
   TerraPayClient.init(
        activity = activity,
        context = this,
        config = config,
        onInitializeStart = {
            findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
        },
    ) { result ->
        findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
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
```

## 🔐 License
This SDK is proprietary and intended for internal or authorized use only. For licensing, contact
TerraPay.

## 📬 Contact
For support or inquiries, email: technology_mobile_apps@terrapay.com
---