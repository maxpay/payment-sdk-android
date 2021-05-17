package com.maxpay.sdk.ui.threeds

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.maxpay.sdk.R
import com.maxpay.sdk.core.FragmentWithToolbar
import com.maxpay.sdk.data.MaxpayResult
import com.maxpay.sdk.data.MaxpayResultStatus
import com.maxpay.sdk.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_three_d_s.*
import java.net.URLEncoder

internal class ThreeDSFragment: FragmentWithToolbar(R.layout.fragment_three_d_s) {
//    private val resultLink: String = "google.com"
    private val callBack = "https://callback.maxpay.com/callback/sale3dSecure" // TODO here must be valid callback
    private val viewModel: MainViewModel by activityViewModels()
    override fun getCurrentViewModel() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_three_d_s, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")

        var reqData: String? = null
        val authResponse = viewModel.viewState.authPaymentResponse.value
        viewModel.viewState.savedSomething
        val url = authResponse?.accessUrl
        if (!url.isNullOrEmpty() ) {
            if (authResponse.pareq.isNullOrEmpty())
                maxpay_webview.loadUrl(url)
            else {
                val pareq = URLEncoder.encode(authResponse?.pareq)
                val md = URLEncoder.encode(authResponse?.reference)
                val termUrl = URLEncoder.encode("https://google.com")
                reqData = "PaReq=$pareq&TermUrl=$termUrl&MD=$md"
                url?.let {
                    maxpay_webview?.postUrl(it, reqData.toByteArray())
                }
            }
        } else {
            viewModel.sendBroadcastResult(
                activity,
                MaxpayResult(MaxpayResultStatus.UNDEF, "Error ${authResponse?.message}")
            )
            return
        }

//        if (authResponse?.pareq.isNullOrEmpty() || authResponse?.reference.isNullOrEmpty()) {
//            viewModel.sendBroadcastResult(
//                activity,
//                MaxpayResult(MaxpayResultStatus.UNDEF, "Error ${authResponse?.message}")
//            )
//            return
//        }
//        var pareq = URLEncoder.encode(authResponse?.pareq)
//        val md = URLEncoder.encode(authResponse?.reference)
//        val termUrl = URLEncoder.encode("https://google.com")
//        reqData = "PaReq=$pareq&TermUrl=$termUrl&MD=$md"
//        url?.let {
//            maxpay_webview?.postUrl(it, reqData.toByteArray())
//        }

        maxpay_webview?.settings?.javaScriptEnabled = true
        maxpay_webview?.settings?.domStorageEnabled = true
        maxpay_webview?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressDialog.cancel()
//                if (url.contains(resultLink)) {
//                    if (parseUri(Uri.parse(url)))
//                        sendBroadcastResult(MaxpayResult.SUCCESS)
//                    else
//                        sendBroadcastResult(null)
//                    this@MaxPayActivity.finish()
//                }
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
                if (url.contains("checkout/info") && Build.VERSION.SDK_INT < 21 && progressDialog != null) {
                    progressDialog.cancel()
                }
                val authRedirect = viewModel.viewState.payPaymentInfo.value?.auth3dRedirectUrl
                if ( (authRedirect != null && url.contains(authRedirect)) || url.contains(callBack) ) {
                    viewModel.viewState.isFromWebView.value = true
                    viewModel.sendBroadcastResult(activity, MaxpayResult(MaxpayResultStatus.SUCCESS, "Success"))
                }
            }

//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                url?.let {
//                    if (it.contains(resultLink)) {
//                        return true
//                    } else
//                        super.shouldOverrideUrlLoading(view, url)
//                }
//
//                return false; // then it is not handled by default action
//            }
        }
    }
    companion object {
        fun newInstance() = ThreeDSFragment()
    }
}