package dev.kichan.marketplace.ui

import android.app.Application
import dev.kichan.marketplace.common.AnalyticsManager
import dev.kichan.marketplace.model.TokenManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(applicationContext)
        AnalyticsManager.init(applicationContext)
    }
}
