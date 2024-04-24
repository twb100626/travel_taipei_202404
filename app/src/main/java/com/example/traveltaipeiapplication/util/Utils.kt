package com.example.traveltaipeiapplication.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object Utils {
    fun getLocaleStringResource(context : Context, requestedLocale: Locale, resourceId: Int) : String {
        val result: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val config = Configuration(context.getResources().getConfiguration())
            config.setLocale(requestedLocale)
            result = context.createConfigurationContext(config).getText(resourceId).toString()
        } else { // support older android versions
            val resources: Resources = context.getResources()
            val conf: Configuration = resources.getConfiguration()
            val savedLocale: Locale = conf.locale
            conf.locale = requestedLocale
            resources.updateConfiguration(conf, null)

            // retrieve resources from desired locale
            result = resources.getString(resourceId)

            // restore original locale
            conf.locale = savedLocale
            resources.updateConfiguration(conf, null)
        }

        return result
    }

    fun getLocaleByIntLang(lang : Int) : Locale {
        return when (lang) {
            0 -> Locale("zh-tw")
            1 -> Locale("zh")
            else -> Locale("en")
        }
    }
}