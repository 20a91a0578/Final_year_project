package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import coil.load
import com.example.woolfabric.R
import com.example.woolfabric.Responses.Models.Information
import com.example.woolfabric.databinding.ActivityViewInfoBinding

class ViewInfo : AppCompatActivity() {
    private lateinit var bind:ActivityViewInfoBinding
    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityViewInfoBinding.inflate(layoutInflater)
        setContentView(bind.root)
        intent.getParcelableExtra<Information>("data")?.let {
            with(it){


                bind.details2.load(path)
                val text="<big>$title</big><br><br>$potray"
                val frame="<iframe width=\"350\" height=\"200\" src=\"https://www.youtube.com/embed/" +
                        "$youtubelink\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                bind.webview.webViewClient=object :WebViewClient()
                {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return false
                    }
                }
                bind.webview.loadData(frame,"text/html","utf-8")
                bind.webview.settings.javaScriptEnabled=true
                bind.details3.text=HtmlCompat.fromHtml(text,FROM_HTML_OPTION_USE_CSS_COLORS)

            }


        }
    }
}