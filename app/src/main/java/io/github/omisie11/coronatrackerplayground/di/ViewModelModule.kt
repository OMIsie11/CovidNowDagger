package io.github.omisie11.coronatrackerplayground.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.omisie11.coronatrackerplayground.ui.global.GlobalViewModel
import io.github.omisie11.coronatrackerplayground.ui.local.LocalViewModel
import io.github.omisie11.coronatrackerplayground.ui.settings.CountriesViewModel
import io.github.omisie11.coronatrackerplayground.util.ViewModelFactory
import io.github.omisie11.coronatrackerplayground.util.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GlobalViewModel::class)
    internal abstract fun globalViewModel(viewModel: GlobalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocalViewModel::class)
    internal abstract fun localViewModel(viewModel: LocalViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    internal abstract fun countriesViewModel(viewModel: CountriesViewModel): ViewModel
}
