package io.github.omisie11.coronatrackerplayground

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.omisie11.coronatrackerplayground.di.AppComponent
import io.github.omisie11.coronatrackerplayground.di.DaggerAppComponent
import io.github.omisie11.coronatrackerplayground.util.CrashReportingTree
import io.github.omisie11.coronatrackerplayground.util.PREFS_KEY_APP_THEME
import javax.inject.Inject
import timber.log.Timber

class MainApplication : Application() {

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()

        AndroidThreeTen.init(this)

        // Logging in Debug build, in release log only crashes
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree()) else
            Timber.plant(CrashReportingTree())

        AppCompatDelegate.setDefaultNightMode(
            translateValueToDayNightMode(
                sharedPrefs.getBoolean(PREFS_KEY_APP_THEME, false)
            )
        )
    }

    private fun translateValueToDayNightMode(value: Boolean): Int = when (value) {
        true -> AppCompatDelegate.MODE_NIGHT_YES
        false -> AppCompatDelegate.MODE_NIGHT_NO
    }
}
