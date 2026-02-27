package cz.gypridilna.inventarizace.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

private const val ACCESS_SYSTEM_URL = "http://209.25.141.22:5513/"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AccessSystemScreen() {
    AndroidView(factory = {
        WebView(it).apply {
            // Match app theme background
            setBackgroundColor(Color.parseColor("#2F353E"))
            
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            
            // Algorithmic darkening for modern Android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                settings.isAlgorithmicDarkeningAllowed = true
            }
            
            // Force Dark On
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }
            
            // Force the User Agent to darken the page
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
                WebSettingsCompat.setForceDarkStrategy(
                    settings, 
                    WebSettingsCompat.DARK_STRATEGY_USER_AGENT_DARKENING_ONLY
                )
            }
            
            loadUrl(ACCESS_SYSTEM_URL)
        }
    })
}
