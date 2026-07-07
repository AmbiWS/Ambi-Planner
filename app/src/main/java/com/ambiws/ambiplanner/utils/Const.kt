package com.ambiws.ambiplanner.utils

object Const {
    const val APP_NAME_SHORT = "APL"
    const val MAX_LOGGING_TAG_LENGTH = 23 // IllegalArgumentException is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25)
    const val LAST_RESET_DATE_KEY = "last_reset_date"
}
