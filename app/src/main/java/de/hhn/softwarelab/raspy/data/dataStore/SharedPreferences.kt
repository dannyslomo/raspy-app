package de.hhn.softwarelab.raspy.data.dataStore

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        const val KEY_TERMS_ACCEPTED = "terms_accepted"
        const val KEY_EMAIL = "email"
        const val KEY_USERNAME = "username"
    }

    var termsAccepted: Boolean
        get() = sharedPreferences.getBoolean(KEY_TERMS_ACCEPTED, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_TERMS_ACCEPTED, value).apply()

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()
}
