package cz.gypridilna.inventarizace.ui.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

private const val GOOGLE_FORM_URL = "https://forms.gle/D6T3nMV5g7nzgd1Y8"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AddItemScreen() {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            
            // Force Dark Mode for WebView if supported
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }
            
            loadUrl(GOOGLE_FORM_URL)
        }
    })
}
