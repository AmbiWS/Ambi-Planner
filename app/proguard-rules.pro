# Сохраняем атрибуты, необходимые для аннотаций, рефлексии и отладки
-keepattributes Signature, RuntimeVisibleAnnotations, AnnotationDefault, InnerClasses, EnclosingMethod, Exceptions, SourceFile, LineNumberTable

# Kotlin Metadata (критично для Kotlin и data-классов)
-keep class kotlin.Metadata { *; }

# Не предупреждать о некоторых распространённых отсутствующих классах
-dontwarn kotlin.**
-dontwarn org.jetbrains.annotations.**

# =============================================
# Android Framework и Jetpack
# =============================================

# Сохраняем Activity, Fragment, ViewModel, Application и т.д.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends androidx.lifecycle.ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Jetpack Compose (синтетические классы и runtime)
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Room Database
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class ** { *; }

# Hilt / Dagger (Dependency Injection)
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.HiltAndroidApp { *; }
-keep class * extends dagger.hilt.android.AndroidEntryPoint { *; }
-keepclassmembers class * {
    @dagger.hilt.android.qualifiers.* <fields>;
    @javax.inject.* <fields>;
    @javax.inject.* <methods>;
}

# =============================================
# Сетевые библиотеки (Retrofit + OkHttp)
# =============================================

-keep class retrofit2.** { *; }
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.KotlinExtensions
-dontwarn okhttp3.**
-dontwarn okio.**

# =============================================
# JSON (Gson / Moshi / Kotlinx Serialization)
# =============================================

# Gson
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Moshi
-keep class com.squareup.moshi.** { *; }
-keep class **JsonAdapter { *; }

# =============================================
# Ваши собственные классы
# =============================================

# Модели / DTO (если используются с Gson, Retrofit или рефлексией)
#-keep class com.ambiws.ambiplanner.models.** { *; }
#-keep class com.ambiws.ambiplanner.data.** { *; }

# Классы с рефлексией или динамическим вызовом
-keepclassmembers class com.ambiws.ambiplanner.** {
    @com.google.gson.annotations.SerializedName <fields>;
    public <init>(...);   # Конструкторы без параметров
}

# Если используете enum, sealed классы или reflection
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
