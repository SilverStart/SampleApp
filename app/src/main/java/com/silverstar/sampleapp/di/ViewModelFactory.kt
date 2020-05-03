package com.silverstar.sampleapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silverstar.sampleapp.di.scope.ActivityScoped
import javax.inject.Inject
import javax.inject.Provider

@ActivityScoped
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T = viewModels[modelClass]?.get() as T
}