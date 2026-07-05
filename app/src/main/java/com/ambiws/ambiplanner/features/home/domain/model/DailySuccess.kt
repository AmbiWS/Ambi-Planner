package com.ambiws.ambiplanner.features.home.domain.model

enum class DailySuccess(val value: Int) {
    ALL_DONE(1),
    SOME_DONE(2),
    NONE_DONE(3);

    companion object {
        fun fromInt(value: Int): DailySuccess {
            return entries.find { it.value == value } ?: NONE_DONE
        }
    }
}
