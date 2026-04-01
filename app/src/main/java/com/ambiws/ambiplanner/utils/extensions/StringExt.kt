package com.ambiws.ambiplanner.utils.extensions

import androidx.core.text.HtmlCompat

fun String.Companion.htmlFormat(format: String, vararg args: Any?): CharSequence {
    return HtmlCompat.fromHtml(
        if (args.isEmpty()) format else String.format(format, *args),
        HtmlCompat.FROM_HTML_MODE_COMPACT
    )
}
