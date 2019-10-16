package com.petmatch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petmatch.qualifier.ViewModelKey
import com.petmatch.viewmodel.AuthViewModel
import com.petmatch.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun authViewModel(viewModel: AuthViewModel): ViewModel
}