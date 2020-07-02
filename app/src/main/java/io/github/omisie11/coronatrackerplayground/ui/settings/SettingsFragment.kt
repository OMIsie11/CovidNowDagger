package io.github.omisie11.coronatrackerplayground.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import io.github.omisie11.coronatrackerplayground.MainApplication
import io.github.omisie11.coronatrackerplayground.R
import io.github.omisie11.coronatrackerplayground.ui.MainActivity
import io.github.omisie11.coronatrackerplayground.ui.local.LocalViewModel
import io.github.omisie11.coronatrackerplayground.util.PREFS_KEY_CHOSEN_LOCATION
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    @Inject
    lateinit var countriesViewModel: CountriesViewModel

    // ViewModel shared between Local and Settings Fragments
    private val localViewModel by viewModels<LocalViewModel>(
        { activity as MainActivity }) { viewModelFactory }

    private lateinit var sharedPrefsListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryPreference = findPreference<ListPreference>(PREFS_KEY_CHOSEN_LOCATION)

        sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFS_KEY_CHOSEN_LOCATION -> {
                    localViewModel.refreshLocalSummary(forceRefresh = true)
                    countryPreference?.summary = getChosenLocation()
                }
            }
        }

        countriesViewModel.getCountries().observe(viewLifecycleOwner, Observer { countries ->
            if (countries != null) {
                countryPreference?.apply {
                    entries = countries.toTypedArray()
                    entryValues = countries.toTypedArray()
                }
            } else countriesViewModel.refreshCountriesData(forceRefresh = true)
        })
    }

    override fun onStart() {
        super.onStart()
        sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
    }

    override fun onResume() {
        super.onResume()
        countriesViewModel.refreshCountriesData(forceRefresh = false)
    }

    override fun onStop() {
        super.onStop()
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(sharedPrefsListener)
    }

    private fun getChosenLocation(): String =
        sharedPrefs.getString(PREFS_KEY_CHOSEN_LOCATION, "Poland") ?: ""
}
