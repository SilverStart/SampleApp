package com.silverstar.sampleapp.di.component

import com.silverstar.sampleapp.di.module.data.DaoModule
import com.silverstar.sampleapp.di.module.data.DbModule
import com.silverstar.sampleapp.di.module.data.NetworkModule
import com.silverstar.sampleapp.di.module.data.ServiceModule
import com.silverstar.sampleapp.di.module.rx.RxModule
import com.silverstar.sampleapp.di.module.ui.ActivityBindingModule
import com.silverstar.sampleapp.di.module.ui.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ContextModule::class,
        NetworkModule::class,
        ServiceModule::class,
        DbModule::class,
        DaoModule::class,
        RxModule::class,
        ActivityBindingModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: DaggerApplication): Builder

        fun build(): ApplicationComponent
    }
}