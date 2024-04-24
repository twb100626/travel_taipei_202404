package com.example.traveltaipeiapplication.fragment

import android.content.Context
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.example.traveltaipeiapplication.R

class WebFragment : Fragment() {

    companion object {
        private val TAG = "[TravelTaipei]" + WebFragment::class.java.simpleName

        fun newInstance(url : String?) : WebFragment {
            var fragment = WebFragment()
            fragment.setUrl(url)
            return fragment;
        }
    }

    private lateinit var webView : WebView
    private var url : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webView)
        init()
    }

    private fun init() {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.setSaveFormData(true);
        webView.settings.setDatabaseEnabled(true);
        webView.settings.setDomStorageEnabled(true);
        webView.settings.setSupportZoom(true);
        webView.settings.setAllowFileAccess(true);
        webView.settings.setAllowContentAccess(true);
        webView.webViewClient = CustomWebViewClient(context)
        url?.let {
            Log.d(TAG, "url :  " + it)
            webView.loadUrl(it)
        }
    }

    fun setUrl(url : String?) {
        this.url = url
    }

    class CustomWebViewClient(val context : Context?) : WebViewClient() {
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            Log.d(TAG, "errorCode= " + error?.errorCode + ", description= " + error?.description)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            Log.d(TAG, "SSL error: " + error.toString())
        }
    }
}