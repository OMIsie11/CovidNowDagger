package io.github.omisie11.coronatrackerplayground.di

import dagger.BindsInstance
import dagger.Component
import io.github.omisie11.coronatrackerplayground.MainApplication
import io.github.omisie11.coronatrackerplayground.ui.MainActivity
import io.github.omisie11.coronatrackerplayground.ui.about.AboutFragment
import io.github.omisie11.coronatrackerplayground.ui.about.used_libraries.UsedLibrariesFragment
import io.github.omisie11.coronatrackerplayground.ui.global.GlobalFragment
import io.github.omisie11.coronatrackerplayground.ui.local.LocalFragment
import io.github.omisie11.coronatrackerplayground.ui.settings.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        MainModule::class,
        NetworkModule::class,
        StorageModule::class,
        ViewModelModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: MainApplication): AppComponent
    }

    fun inject(app: MainApplication)
    fun inject(activity: MainActivity)
    fun inject(fragment: GlobalFragment)
    fun inject(fragment: LocalFragment)
    fun inject(fragment: AboutFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: UsedLibrariesFragment)
}
