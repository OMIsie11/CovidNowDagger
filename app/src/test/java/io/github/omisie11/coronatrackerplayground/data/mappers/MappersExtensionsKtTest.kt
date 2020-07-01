package io.github.omisie11.coronatrackerplayground.data.mappers

import io.github.omisie11.coronatrackerplayground.data.local.model.Country
import io.github.omisie11.coronatrackerplayground.data.local.model.GlobalSummary
import io.github.omisie11.coronatrackerplayground.data.local.model.LocalSummary
import io.github.omisie11.coronatrackerplayground.data.remote.model.CountriesRemote
import io.github.omisie11.coronatrackerplayground.data.remote.model.GlobalSummaryRemote
import io.github.omisie11.coronatrackerplayground.data.remote.model.LocalSummaryRemote
import io.github.omisie11.coronatrackerplayground.utils.testCountry1
import io.github.omisie11.coronatrackerplayground.utils.testCountry2
import io.github.omisie11.coronatrackerplayground.utils.testCountry3
import io.github.omisie11.coronatrackerplayground.utils.testCountry4
import io.github.omisie11.coronatrackerplayground.utils.testCountry5
import io.github.omisie11.coronatrackerplayground.utils.testCountry6
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote1
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote2
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote3
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote4
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote5
import io.github.omisie11.coronatrackerplayground.utils.testCountryRemote6
import io.github.omisie11.coronatrackerplayground.utils.testGlobalSummary
import io.github.omisie11.coronatrackerplayground.utils.testGlobalSummaryRemote
import io.github.omisie11.coronatrackerplayground.utils.testLocalSummary
import io.github.omisie11.coronatrackerplayground.utils.testLocalSummaryRemote
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MappersExtensionsKtTest {

    private val testCountriesRemote = CountriesRemote(
        countries = listOf(
            testCountryRemote1,
            testCountryRemote2,
            testCountryRemote3,
            testCountryRemote4,
            testCountryRemote5,
            testCountryRemote6
        )
    )

    private val testCountriesList = listOf(
        testCountry1,
        testCountry2,
        testCountry3,
        testCountry4,
        testCountry5,
        testCountry6
    )

    @Test
    fun mapToLocalSummary_Global_validObject() {
        val expected = testGlobalSummary
        val result = testGlobalSummaryRemote.mapToLocalSummary()
        assertEquals(expected, result)
    }

    @Test
    fun mapToLocalSummary_Global_withNulls() {
        val testDataWithNulls = GlobalSummaryRemote(
            null,
            null,
            null,
            null,
            null
        )
        val expected = GlobalSummary(
            null,
            null,
            null,
            null,
            "",
            ""
        )
        val result = testDataWithNulls.mapToLocalSummary()
        assertEquals(expected, result)
    }

    @Test
    fun mapToLocalSummary_Local_validObject() {
        val expected = testLocalSummary
        val result = testLocalSummaryRemote.mapToLocalSummary()
        assertEquals(expected, result)
    }

    @Test
    fun mapToLocalSummary_Local_withNulls() {
        val testDataWithNulls = LocalSummaryRemote(
            null,
            null,
            null,
            null
        )
        val expected = LocalSummary(
            null,
            null,
            null,
            null,
            ""
        )
        val result = testDataWithNulls.mapToLocalSummary()
        assertEquals(expected, result)
    }

    @Test
    fun mapToLocalCountry_validData() {
        val expected = testCountriesList
        val result = testCountriesRemote.mapToLocalCountryList()
        assertEquals(expected, result)
    }

    @Test
    fun mapToLocalCountry_emptyList() {
        val testCountriesEmpty = CountriesRemote(emptyList())
        val expected = emptyList<Country>()
        val result = testCountriesEmpty.mapToLocalCountryList()
        assertEquals(expected, result)
    }
}
