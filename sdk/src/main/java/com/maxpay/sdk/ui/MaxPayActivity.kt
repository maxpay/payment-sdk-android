package com.maxpay.sdk.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.utils.Constants

class MaxPayActivity : AppCompatActivity() {
    var mWebView: WebView? = null
    val resultLink = "/api/portmone/web-view/result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = 1
        this.setTheme(16974124)
//        this.setContentView(R.layout.activity_portmone)
//        mWebView = findViewById(R.id.portmone_webview)
//        findViewById<ImageView>(R.id.close_webview).setOnClickListener {
//            sendBroadcastResult(null)
//            this@MaxPayActivity.finish()
//        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        var reqData: String? = null
        val url = this.intent.getStringExtra(Constants.Companion.Extra.RETURN_URL)
        this.intent.getStringExtra(Constants.Companion.Extra.MAXPAY_DATA)?.let {
            val postData = this.intent.getStringExtra(Constants.Companion.Extra.MAXPAY_DATA)
            reqData = "bodyRequest=$postData&typeRequest=json"
        }?: kotlin.run {

            //TODO 3ds required
//            val pareq = this.intent.getStringExtra(Constants.Companion.Extra.PORTMONE_PARAQ)
//            val md = this.intent.getStringExtra(Constants.Companion.Extra.PORTMONE_MD)
//            val termUrl = this.intent.getStringExtra(Constants.Companion.Extra.PORTMONE_TERM_URL)
//            reqData = "PaReq=$pareq&MD=$md&TermUrl=$termUrl"
        }
        Log.d("Jackk", " PaymentActivity $reqData")

//        mWebView?.postUrl(url, reqData?.toByteArray())

        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.settings?.domStorageEnabled = true
        mWebView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Log.d("Jackk", "OnPageFinished $url")
                progressDialog.cancel()
                if (url.contains(resultLink)) {
                    if (parseUri(Uri.parse(url)))
                        sendBroadcastResult(MaxpayResult.SUCCESS)
                    else
                        sendBroadcastResult(null)
                    this@MaxPayActivity.finish()
                }
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                Log.d("Jackkk", "OnLoadResource $url")
                super.onLoadResource(view, url)
                if (url.contains("checkout/info") && VERSION.SDK_INT < 21 && progressDialog != null) {
                    progressDialog.cancel()
                }
                if (url.contains(resultLink)) {
                    try {
                        if (parseUri(Uri.parse(url)))
                            sendBroadcastResult(MaxpayResult.SUCCESS)
                        else
                            sendBroadcastResult(null)
                    } catch (var6: Exception) {
                        var6.printStackTrace()
                        sendBroadcastResult(MaxpayResult.UNDEF)
                    }
                    this@MaxPayActivity.finish()
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    if (it.contains(resultLink)) {
                        return true
                    } else
                        super.shouldOverrideUrlLoading(view, url)
                }

                return false; // then it is not handled by default action
            }
        }
    }

    override fun onDestroy() {
        mWebView?.destroy()
        this.sendBroadcast(Intent(Constants.MAXPAY_CALLBACK_BROADCAST))
        super.onDestroy()
    }

    private fun parseUri(uri: Uri): Boolean =
        uri.getQueryParameter("success") == "1"

    private fun sendBroadcastResult(data: MaxpayResult?) {
        val intent = Intent(Constants.MAXPAY_CALLBACK_BROADCAST)
        data?.let {
            intent.setPackage(this@MaxPayActivity.packageName)
            intent.putExtra("data", data)
        }
        this@MaxPayActivity.sendBroadcast(intent)

    }

}