package org.japancv.multiplescreens

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.view.Display
import androidx.window.layout.WindowMetricsCalculator

fun Activity.logDisplayInfo(tag: String, display: Display) {
    // Window metrics: https://developer.android.com/guide/topics/large-screens/multi-window-support#window_metrics
    val windowMetrics =
        WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)

    // Use [android.content.res.Configuration] instead of deprecated methods:
    // https://developer.android.com/guide/topics/large-screens/multi-window-support#deprecated_methods
    val config: Configuration = resources.configuration
    Log.d(tag, "Display DPI: [${display.displayId}]" +
            " densityDpi: ${config.densityDpi} ( H: ${config.screenHeightDp} x W: ${config.screenWidthDp})" +
            " Pixels(H: ${windowMetrics.bounds.height()} x W: ${windowMetrics.bounds.width()})")
}