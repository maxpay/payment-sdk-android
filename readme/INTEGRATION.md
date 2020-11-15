# Basic Integration

## 1. Set up MaxPay

First of all [register](https://my.maxpay.com/#/signup) on site [Maxpay](https://maxpay.com/) and get merchant login and password.



## 2. Add .aar as dependency to your project

### 1. Download .aar file.

### 2. Import downloaded module to your android project

Click new -> new module...

<img src="C:\Users\dev\Documents\CreateNewModule.png" style="zoom:73%;" />



After clicking next you will see import module from library window. Find location of your .aar file, choose subproject name and click Finish button

<img src="C:\Users\dev\Documents\ChooseSubProjectName.png" alt="ChooseSubProjectName" style="zoom:75%;" />



### 3. Add dependency to Maxpay SDK

To use MaxpaySDK inside your app, you need to provide dependency to maxpay. Click File -> Project Structure -> Choose your app module, click + button -> module dependency here you can find "maxpay_sdk" module

<img src="C:\Users\dev\Documents\add_dependency.png" style="zoom:75%;" />



Now you can use SDK inside your application



### 4. Dependencies

To use MaxpaySDK inside your app, you need to provide dependencies

```groovy
	// You can use versions upper this
    implementation 'com.google.android.material:material:1.2.1'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.1'

    implementation 'androidx.navigation:navigation-fragment:2.3.1'
    implementation 'androidx.navigation:navigation-ui:2.3.1'
    implementation "com.squareup.retrofit2:retrofit:2.7.2"
    implementation 'org.koin:koin-androidx-viewmodel:2.0.1'
    implementation 'org.koin:koin-androidx-scope:2.0.1'
    implementation 'org.koin:koin-android:2.0.1'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
    implementation 'io.reactivex.rxjava2:rxkotlin:2.3.0'
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.14.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation "androidx.browser:browser:1.2.0"
}
```



### 5. Configure application theme

In Maxpay sdk we are using material theme for UI, this is required to use globally material theme. 

#### 5.1 Change theme of your app to Material

You can change whole theme of your app to material theme "Theme.MaterialComponents.Light.NoActionBar" 

```xml
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:buttonStyle">@style/AppPrimaryButton</item>
        <item name="materialButtonStyle">@style/AppPrimaryButton</item>
        <item name="android:actionMenuTextColor">@color/colorWhite</item>
    </style>
```

Here you can find other material themes:
https://material.io/develop/android/docs/getting-started#bridge-themes



This change will affect your application theme, if you don`t want to make it happen:

	#### 5.2 Use material bridge theme

If you cannot change your theme to inherit from a Material Components theme, you can inherit from a Material Components **Bridge** theme.

```xml
<style name="Theme.MyApp" parent="Theme.MaterialComponents.Light.Bridge">
    <!-- ... -->
</style>
```

Both Theme.MaterialComponents and Theme.MaterialComponents.Light have .Bridge themes:

- Theme.MaterialComponents.Bridge
- Theme.MaterialComponents.Light.Bridge
- Theme.MaterialComponents.NoActionBar.Bridge
- Theme.MaterialComponents.Light.NoActionBar.Bridge
- Theme.MaterialComponents.Light.DarkActionBar.Bridge

Bridge themes inherit from AppCompat themes, but also define the new Material Components theme attributes for you. If you use a bridge theme, you can start using Material Design components without changing your app theme.

	#### 5.3 Without changing theme

If  you don`t want to change your current theme, you can use this solution to make MaxPaySDK work:

```xml
<style name="Theme.MyApp" parent="Theme.AppCompat">

  <!-- Original AppCompat attributes. -->
  <item name="colorPrimary">@color/my_app_primary_color</item>
  <item name="colorSecondary">@color/my_app_secondary_color</item>
  <item name="android:colorBackground">@color/my_app_background_color</item>
  <item name="colorError">@color/my_app_error_color</item>

  <!-- New MaterialComponents attributes. -->
  <item name="colorPrimaryVariant">@color/my_app_primary_variant_color</item>
  <item name="colorSecondaryVariant">@color/my_app_secondary_variant_color</item>
  <item name="colorSurface">@color/my_app_surface_color</item>
  <item name="colorOnPrimary">@color/my_app_color_on_primary</item>
  <item name="colorOnSecondary">@color/my_app_color_on_secondary</item>
  <item name="colorOnBackground">@color/my_app_color_on_background</item>
  <item name="colorOnError">@color/my_app_color_on_error</item>
  <item name="colorOnSurface">@color/my_app_color_on_surface</item>
  <item name="scrimBackground">@color/mtrl_scrim_color</item>
  <item name="textAppearanceHeadline1">@style/TextAppearance.MaterialComponents.Headline1</item>
  <item name="textAppearanceHeadline2">@style/TextAppearance.MaterialComponents.Headline2</item>
  <item name="textAppearanceHeadline3">@style/TextAppearance.MaterialComponents.Headline3</item>
  <item name="textAppearanceHeadline4">@style/TextAppearance.MaterialComponents.Headline4</item>
  <item name="textAppearanceHeadline5">@style/TextAppearance.MaterialComponents.Headline5</item>
  <item name="textAppearanceHeadline6">@style/TextAppearance.MaterialComponents.Headline6</item>
  <item name="textAppearanceSubtitle1">@style/TextAppearance.MaterialComponents.Subtitle1</item>
  <item name="textAppearanceSubtitle2">@style/TextAppearance.MaterialComponents.Subtitle2</item>
  <item name="textAppearanceBody1">@style/TextAppearance.MaterialComponents.Body1</item>
  <item name="textAppearanceBody2">@style/TextAppearance.MaterialComponents.Body2</item>
  <item name="textAppearanceCaption">@style/TextAppearance.MaterialComponents.Caption</item>
  <item name="textAppearanceButton">@style/TextAppearance.MaterialComponents.Button</item>
  <item name="textAppearanceOverline">@style/TextAppearance.MaterialComponents.Overline</item>

</style>
```

Both Theme.MaterialComponents and Theme.MaterialComponents.Light have .Bridge themes:



## 2 Prepare data

In your application create all necessary forms and request to collect all data about merchant, customer and order.
On the basis of the information obtained create **MaxPayInitData**, **MaxpayPaymentData**.

### 2.1 Prepare merchant data

**MXPMerchant** provides information about merchant.

| Property        | Type        | Description                                   | Note     |
| --------------- | ----------- | --------------------------------------------- | -------- |
| apiVersion      | Int         | Maxpay API version                            | required |
| accountName     | String      | Merchant account                              | required |
| accountPassword | String      | Merchant password                             | required |
| theme           | MaxPayTheme | Custom theme, to change payment screen colors | optional |

**Sale3DRedirect** is just a wrapper for two redirection links.

| Property    | Type   | Description                                                  | Note     |
| ----------- | ------ | ------------------------------------------------------------ | -------- |
| callbackURL | String | Once the order has been processed merchant will receive a final response (callback)  regarding the transaction status to this URL | required |
| redirectURL | String | Once the verification process has been done a customer will be redirected to this URL | required |

### 2.2 Prepare customer data

**MXPCustomer** provides information about customer.

| Property  | Type              | Description                                                  | Note     |
| --------- | ----------------- | ------------------------------------------------------------ | -------- |
| firstName | String            | The first name of the customer                               | required |
| lastName  | String            | The last name of the customer                                | required |
| phone     | String            | Customer's phone number.                                     | optional |
| email     | String            | Customer's email address                                     | required |
| ip        | String            | Customer's IP address. Not all acquirers support IPv6 format | required |
| birthday  | String            | Date of birth of the customer                                | optional |




| Property | Type   | Description                                                  | Note     |
| -------- | ------ | ------------------------------------------------------------ | -------- |
| country  | String | Customer's country, ISO 3166-1, alpha-3                      | required |
| state    | String | Customer's state                                             | optional |
| city     | String | Customer's city                                              | optional |
| address  | String | Customer's zip code                                          | optional |
| zip      | String | Customer's IP address. Not all acquirers support IPv6 format | optional |

### 2.3 Prepare order data

**MXPOrder** provides information about order.

| Property            | Type            | Description                 | Note     |
| ------------------- | --------------- | --------------------------- | -------- |
| transactionUniqueID | String          | Unique transaction Id       | required |
| transactionType     | TransactionType | Type of the transaction     | required |
| amount              | Float           | Amount of the transaction   | required |
| currency            | Currency        | Currency of the transaction | required |

Additional sutypes for **MXPCustomer**:

**TransactionType** is transaction types supported by Maxpay.

| State  |
| ------ |
| SALE   |
| AUTH   |
| SALE3D |
| AUTH3D |

## 3 Set up an SDKFacade

Class **SDKFacade** provides information to create payment request to Maxpay service.

```kotlin
        val sdk: SDKFacade = SdkFacadeImpl(
            MaxPayInitData(
                accountName = "acc_name",
                accountPassword = "password",
                apiVersion = 1,
                theme = null// Or you can add your theme manually by filling MaxpayTheme data class
            )
            )

			val maxPaymentData = MaxpayPaymentData() // Fill data class
            sdk.pay(maxPaymentData, object: MaxpayCallback {
                override fun onResponseSuccess(result: MaxpayResult?) {
                    _viewState.maxpayResult.value = result
                }

                override fun onResponceError(result: MaxpayResult?) {
                    _viewState.maxpayResult.value = result
                }

            })

```



**language** - Maxpay provides different languages for the payment page localization. Available languages English, Russian, German, French, Portuguese, Italian, Spanish, Turkish, Swedish, Norwegian, Danish, Finnish, Dutch, Irish, Polish, Lithuanian.

## 4 Set up an MaxPayTheme

**MaxPayTheme** objects can be used to visually style Maxpay-provided UI.

 Here you can find sample how to customize UI by filling MaxPayTheme object



     MaxPayTheme(
         fieldTitleColor = Color.RED,
         fieldBackgroundColor = Color.YELLOW,
         fieldTextColor = Color.CYAN,
         errorColor = Color.YELLOW,
         backgroundColor = Color.CYAN,
         navigationBarColor = Color.GREEN,
         navigationBarTitleColor = Color.BLACK,
         hyperlinkColor = Color.RED,
         headerAmountColor = Color.RED,
         headerTitleColor = Color.GREEN,
         headerAmountFont = font,
         headerStandardTitleFont = font,
         headerLargeTitleFont = font,
         headerSeparatorColor = Color.RED,
         disabledButtonBackgroundColor = Color.BLACK,
         disabledButtonTitleColor = Color.WHITE,
         enabledButtonBackgroundColor =  Color.RED,
         enabledButtonTitleColor =  Color.BLACK
     )



## 6 Handle the user's billing address

**Out of scope**

Explanation difference between shipping and billing addresses.
Best practies how to prefill billing address if bank support AVS (address verification system).



## 8 Test the integration

[Test data from Maxpay.](https://maxpay.com/docs/#test-mode)



## Integration prebuilt UI
[How to use prebuilt UI see here](README/INTEGRATION.md).

## Examples
With framework you downloaded example project.
In this project you can use simple form, when you can fast create payment auth or sale request wit, choose currency and enter payment amount. Also you can change customer data and decide show or not billing address on payment screen.

**Warning. Billing address on payment screen is out of scope MVP. Not all functions will work correctly.**

![Simple form](README/simple_form.png)

**Warning. Shop is out of scope MVP. Not all functions will work correctly.**

Also you can run shop imitation with cart and shipment form. Currency, transaction type and theme can change in Settings menu.

![Shop](README/shop.png)