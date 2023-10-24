package com.airbnb.mvrx.hellohilt

import com.airbnb.mvrx.hellohilt.screen.detail.DetailViewModel
import com.airbnb.mvrx.hellohilt.screen.timeline.TimelineViewModel
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TimelineViewModel::class)
    fun timelineViewModelFactory(factory: TimelineViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun detailViewModelFactory(factory: DetailViewModel.Factory): AssistedViewModelFactory<*, *>
}
