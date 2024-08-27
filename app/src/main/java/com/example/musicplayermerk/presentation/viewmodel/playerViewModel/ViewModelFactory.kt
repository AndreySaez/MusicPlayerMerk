package com.example.musicplayermerk.presentation.viewmodel.playerViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayermerk.presentation.di.FragmentScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@FragmentScope
class ViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as? T
            ?: throw IllegalStateException("Can't find viewModel")
    }
}