package com.splitsquad.splitpay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.splitsquad.splitpay.R.id.webView


class HomeFragment : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инфляция макета фрагмента
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация WebView с использованием безопасного вызова
        webView = view.findViewById<WebView>(R.id.webView) ?: return

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Загружаем URL
        webView.loadUrl("http://158.160.77.210")
    }
}