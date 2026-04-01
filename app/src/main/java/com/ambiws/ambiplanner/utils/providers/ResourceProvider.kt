package com.ambiws.ambiplanner.utils.providers

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.ambiws.ambiplanner.utils.extensions.htmlFormat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

interface ResourceProvider {

    fun getString(@StringRes res: Int, vararg args: Any): String

    fun getHtml(@StringRes res: Int, vararg args: Any): CharSequence

    fun getStringArray(@ArrayRes res: Int): Array<out String>

    fun getColor(@ColorRes color: Int): Int

    fun getDrawable(@DrawableRes icon: Int): Drawable?

    fun getRaw(@RawRes raw: Int): InputStream

    fun getRawString(@RawRes raw: Int): String

    fun getSP(value: Float): Float

    fun getDP(value: Float): Float

    fun getTypedValue(value: Float, unit: Int): Float

    fun getJsonDataFromAsset(fileName: String): String?
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(@StringRes res: Int, vararg args: Any): String {
        return context.getString(res, *args)
    }

    override fun getHtml(@StringRes res: Int, vararg args: Any): CharSequence {
        return String.htmlFormat(context.getString(res), *args)
    }

    override fun getStringArray(@ArrayRes res: Int): Array<out String> {
        return context.resources.getStringArray(res)
    }

    override fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    override fun getDrawable(@DrawableRes icon: Int): Drawable? {
        return AppCompatResources.getDrawable(context, icon)
    }

    override fun getRaw(@RawRes raw: Int): InputStream {
        return context.resources.openRawResource(raw)
    }

    override fun getRawString(@RawRes raw: Int): String {
        val inputReader = InputStreamReader(getRaw(raw))
        val bufferedReader = BufferedReader(inputReader)
        return bufferedReader.use {
            it.readText()
        }
    }

    override fun getSP(value: Float): Float {
        return getTypedValue(value, TypedValue.COMPLEX_UNIT_SP)
    }

    override fun getDP(value: Float): Float {
        return getTypedValue(value, TypedValue.COMPLEX_UNIT_DIP)
    }

    override fun getTypedValue(value: Float, unit: Int): Float {
        return TypedValue.applyDimension(unit, value, context.resources.displayMetrics)
    }

    override fun getJsonDataFromAsset(fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}
