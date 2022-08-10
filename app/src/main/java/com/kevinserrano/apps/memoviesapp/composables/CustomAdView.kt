package com.kevinserrano.apps.memoviesapp.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.kevinserrano.apps.memoviesapp.R

@Composable
fun GoogleBannerAdView(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER,
    stringIdAdUnitId: Int = R.string.ad_id_banner
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(adSize)
                adUnitId = context.getString(stringIdAdUnitId)
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}