package io.github.omisie11.coronatrackerplayground.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import io.github.omisie11.coronatrackerplayground.MainApplication
import io.github.omisie11.coronatrackerplayground.R
import io.github.omisie11.coronatrackerplayground.databinding.ActivityMainBinding
import io.github.omisie11.coronatrackerplayground.util.PREFS_KEY_APP_THEME
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    private lateinit var sharedPrefsListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).appComponent.inject(this)

        setTheme(R.style.Theme_CovidTracker)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default values of preferences for first app launch (third argument set
        // to false ensures that this is won't set user settings to defaults with every call)
        PreferenceManager.setDefaultValues(this, R.xml.app_preferences, false)

        setupNavigation()

        sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFS_KEY_APP_THEME ->
                    AppCompatDelegate.setDefaultNightMode(
                        translateValueToDayNightMode(
                            sharedPrefs.getBoolean(PREFS_KEY_APP_THEME, false)
                        )
                    )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
    }

    override fun onStop() {
        super.onStop()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(sharedPrefsListener)
    }

    private fun translateValueToDayNightMode(value: Boolean): Int = when (value) {
        true -> AppCompatDelegate.MODE_NIGHT_YES
        false -> AppCompatDelegate.MODE_NIGHT_NO
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}
