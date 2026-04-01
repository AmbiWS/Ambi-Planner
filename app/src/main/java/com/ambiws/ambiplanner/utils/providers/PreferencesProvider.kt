package com.ambiws.ambiplanner.utils.providers

import android.content.Context

interface PreferencesProvider {

    fun saveString(key: String, value: String?)

    fun getString(key: String): String?

    fun saveInt(key: String, value: Int)

    fun getInt(key: String): Int

    fun saveLong(key: String, value: Long)

    fun getLong(key: String): Long

    fun saveBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean

    fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean

    fun clearAll()

    fun clear(key: String)
}

class PreferencesProviderImpl(context: Context) : PreferencesProvider {

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun saveLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun getBooleanWithDefaultValue(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    override fun clear(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    companion object {
        private const val PREFERENCES_NAME = "ap_preferences"
    }
}
